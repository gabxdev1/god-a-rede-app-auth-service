package br.com.gabxdev.infra.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "http-clients")
public record HttpClientProps(
        UserService userService
) {
    public record UserService(String urlBase,
                       Endpoints endpoints) {

    }
    public record Endpoints(String criarProfile) {
    }
}
