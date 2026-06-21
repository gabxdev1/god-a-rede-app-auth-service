package br.com.gabxdev.domain.model;

import java.time.Instant;
import java.util.UUID;

public record Profile(
        UUID userId,

        String firstName,

        String lastName,

        String bio,

        String avatarUrl,

        Instant createdAt,

        String username
) {
}
