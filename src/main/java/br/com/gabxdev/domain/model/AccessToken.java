package br.com.gabxdev.domain.model;

public record AccessToken(
        String accessToken,

        String refreshToken
) {
}
