package br.com.gabxdev.application;

import br.com.gabxdev.domain.exception.ExpiredJwtException;
import br.com.gabxdev.domain.ports.in.JwtInboundPort;
import br.com.gabxdev.infra.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtUseCase implements JwtInboundPort {
    private final SecretKey key;
    private final SecurityProperties securityProperties;

    @Override
    public String generateToken(String userId, Instant now) {
        return Jwts.builder()
                .subject(userId)
                .issuer("god-a-rede")
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(securityProperties.jwt().expiration())))
                .id(UUID.randomUUID().toString())
                .signWith(key)
                .compact();
    }

    public UUID validate(String token) {
        var claims = parse(token).getPayload();

        if (!claims.getExpiration().after(new Date())) {
            throw new ExpiredJwtException("Token expired at %s".formatted(claims.getExpiration()));
        }

        return UUID.fromString(claims.getSubject());
    }

    private Jws<Claims> parse(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
    }
}
