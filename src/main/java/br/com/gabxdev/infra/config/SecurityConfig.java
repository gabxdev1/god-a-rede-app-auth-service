package br.com.gabxdev.infra.config;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final ResourceLoader resourceLoader;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    RSAPrivateKey privateKey() throws Exception {
        var pemContent = readPem("classpath:private.pem");
        var privateKeyPem = pemContent
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        var decodedKey = Base64.getDecoder().decode(privateKeyPem);
        var keySpec = new PKCS8EncodedKeySpec(decodedKey);
        var keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }


    @Bean
    RSAKey rsaKey(RSAPublicKey publicKey) {
        return new RSAKey.Builder(publicKey)
                .keyID("http://localhost:8081")
                .algorithm(JWSAlgorithm.RS256)
                .keyUse(KeyUse.SIGNATURE)
                .build();
    }


    @Bean
    RSAPublicKey publicKey() throws Exception {
        var pemContent = readPem("classpath:public.pem");
        var publicKeyPem = pemContent
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        var decodedKey = Base64.getDecoder().decode(publicKeyPem);
        var keySpec = new X509EncodedKeySpec(decodedKey);
        var keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    private String readPem(String path) throws Exception {
        Resource resource = resourceLoader.getResource(path);

        return new String(
                resource.getContentAsByteArray(),
                StandardCharsets.UTF_8
        );
    }
}
