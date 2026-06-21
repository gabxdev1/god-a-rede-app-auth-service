package br.com.gabxdev.infra.adapter.in.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/.well-known")
public class PublicKeyController {

    private final RSAKey rsaKey;

    @GetMapping("/jwks.json")
    public Map<String, Object> jwks() {
        return new JWKSet(rsaKey)
                .toJSONObject();
    }

    @GetMapping("/openid-configuration")
    public Map<String, Object> configuration() {

        return Map.of(
                "issuer", "http://localhost:8081",
                "jwks_uri", "http://localhost:8081/.well-known/jwks.json"
        );
    }
}
