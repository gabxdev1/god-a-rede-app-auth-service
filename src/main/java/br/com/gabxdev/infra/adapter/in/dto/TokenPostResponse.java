package br.com.gabxdev.infra.adapter.in.dto;

public record TokenPostResponse(
        String accessToken,

        String refreshToken) {
}
