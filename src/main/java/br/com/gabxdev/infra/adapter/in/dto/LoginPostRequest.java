package br.com.gabxdev.infra.adapter.in.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginPostRequest(
        @NotBlank
        @Email
        String email,

        @NotBlank
        String password
) {
}
