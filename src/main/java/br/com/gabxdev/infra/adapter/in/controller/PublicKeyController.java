package br.com.gabxdev.infra.adapter.in.controller;

import br.com.gabxdev.infra.properties.SecurityProperties;
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

    private final SecurityProperties securityProperties;

    @GetMapping("/jwks.json")
    public Map<String, Object> jwks() {
        return new JWKSet(rsaKey)
                .toJSONObject();
    }

    @GetMapping("/openid-configuration")
    public Map<String, Object> configuration() {

        return Map.of(
                "issuer", securityProperties.jwt().issuer(),
                "jwks_uri", securityProperties.jwt().issuer().concat("/.well-known/jwks.json")
        );
    }
}
