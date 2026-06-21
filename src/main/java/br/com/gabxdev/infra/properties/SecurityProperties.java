package br.com.gabxdev.infra.properties;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "security")
@Validated
public record SecurityProperties(
        @Valid
        JwtProperties jwt
) {
    @Valid
    public record JwtProperties(
            @NotNull
            @Positive
            Integer expiration
    ) {
    }
}
