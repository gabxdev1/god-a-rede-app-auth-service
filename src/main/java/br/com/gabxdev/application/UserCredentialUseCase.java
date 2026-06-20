package br.com.gabxdev.application;

import br.com.gabxdev.domain.exception.BadCredentialsException;
import br.com.gabxdev.domain.exception.ExpiredJwtException;
import br.com.gabxdev.domain.exception.NotFoundException;
import br.com.gabxdev.domain.exception.TokenRevokedException;
import br.com.gabxdev.domain.model.AccessToken;
import br.com.gabxdev.domain.model.RefreshToken;
import br.com.gabxdev.domain.model.UserCredential;
import br.com.gabxdev.domain.ports.in.JwtInboundPort;
import br.com.gabxdev.domain.ports.in.UserCredentialInboundPort;
import br.com.gabxdev.domain.ports.out.RefreshTokenOutputPort;
import br.com.gabxdev.domain.ports.out.UserCredentialOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserCredentialUseCase implements UserCredentialInboundPort {

    private final UserCredentialOutputPort userCredentialOutputPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtInboundPort jwtInboundPort;
    private final RefreshTokenOutputPort refreshTokenOutputPort;

    @Override
    public UserCredential signUp(UserCredential userCredential) {
        log.info("Signing up user with email: {}", userCredential.getEmail());

        var saved = userCredentialOutputPort.save(userCredential);

        log.info("User with email: {} signed up successfully with id: {}", saved.getEmail(), saved.getId());

        return saved;
    }

    @Override
    public AccessToken authenticate(String email, String password) {
        log.info("Authenticating user with email: {}", email);

        var userCredential = userCredentialOutputPort.findByEmailOrThrowNotFound(email);

        if (!passwordEncoder.matches(password, userCredential.getPasswordHash())) {
            throw new BadCredentialsException("E-mail ou senha inválidos");
        }

        var accessToken = generateRefreshToken(userCredential.getId());

        log.info("User with email: {} authenticated successfully, access token generated", email);

        return accessToken;
    }

    @Override
    public AccessToken refresh(String token) {
        log.info("Refreshing access token with refresh token: {}", token);

        var refreshToken = refreshTokenOutputPort.findByTokenOrThrowNotFound(token);

        if (refreshToken.getRevoked()) {
            throw new TokenRevokedException("Refresh token with token: %s has been revoked".formatted(token));
        }

        if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
            throw new ExpiredJwtException("Refresh token with token: %s has expired".formatted(token));
        }

        var userCredential = userCredentialOutputPort.findByIdOrThrowNotFound(refreshToken.getUserId());

        refreshToken.setRevoked(Boolean.TRUE);
        refreshTokenOutputPort.save(refreshToken);

        var accessToken = generateRefreshToken(userCredential.getId());

        log.info("Access token refreshed successfully with refresh token: {}", token);

        return accessToken;
    }

    @Override
    public void logout(String token) {
        try {
            var refreshToken = refreshTokenOutputPort.findByTokenOrThrowNotFound(token);

            refreshToken.setRevoked(Boolean.TRUE);

            refreshTokenOutputPort.save(refreshToken);
        } catch (NotFoundException ignored) {
        }
    }

    private AccessToken generateRefreshToken(UUID userId) {
        var now = Instant.now();
        var expiresAt = now.plus(30, ChronoUnit.DAYS);

        var token = jwtInboundPort.generateToken(userId.toString(), now);

        var refreshTokenValue = UUID.randomUUID().toString();
        var refreshToken = new RefreshToken();
        refreshToken.setUserId(userId);
        refreshToken.setToken(refreshTokenValue);
        refreshToken.setCreatedAt(now);
        refreshToken.setRevoked(Boolean.FALSE);
        refreshToken.setExpiresAt(expiresAt);

        var refreshTokenSaved = refreshTokenOutputPort.save(refreshToken);

        return new AccessToken(token, refreshTokenSaved.getToken());
    }
}
