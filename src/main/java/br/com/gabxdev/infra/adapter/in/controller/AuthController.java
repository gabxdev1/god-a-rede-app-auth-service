package br.com.gabxdev.infra.adapter.in.controller;

import br.com.gabxdev.domain.ports.in.UserCredentialInboundPort;
import br.com.gabxdev.infra.adapter.in.dto.LoginPostRequest;
import br.com.gabxdev.infra.adapter.in.dto.SignUpPostRequest;
import br.com.gabxdev.infra.adapter.in.dto.TokenPostResponse;
import br.com.gabxdev.infra.adapter.in.mapper.AuthInboundMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final AuthInboundMapper authInboundMapper;
    private final UserCredentialInboundPort userCredentialInboundPort;

    @PostMapping(path = "/sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpPostRequest request) {
        var userCredential = authInboundMapper.toUserCredential(request, passwordEncoder);
        userCredentialInboundPort.signUp(userCredential);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(path = "/sign-in")
    public ResponseEntity<TokenPostResponse> signIn(@Valid @RequestBody LoginPostRequest request) {
        var accessToken = userCredentialInboundPort.authenticate(request.email(), request.password());
        var tokenResponse = authInboundMapper.toTokenPostResponse(accessToken);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping(path = "/refresh/{refreshToken}")
    public ResponseEntity<TokenPostResponse> refresh(@UUID @NotNull
                                                     @PathVariable String refreshToken) {
        var accessToken = userCredentialInboundPort.refresh(refreshToken);
        var tokenResponse = authInboundMapper.toTokenPostResponse(accessToken);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/logout/{refreshToken}")
    public ResponseEntity<Void> logout(@PathVariable String refreshToken
    ) {
        userCredentialInboundPort.logout(refreshToken);

        return ResponseEntity.ok().build();
    }
}
