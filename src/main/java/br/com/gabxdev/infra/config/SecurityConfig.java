package br.com.gabxdev.infra.config;

import br.com.gabxdev.infra.properties.SecurityProperties;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

@Configuration
public class SecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecretKey key(SecurityProperties securityProperties) {
        var decoded = Decoders.BASE64.decode(securityProperties.jwt().secret());
        return Keys.hmacShaKeyFor(decoded);
    }
}
