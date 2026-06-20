package br.com.gabxdev.infra.integration.repository.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Data
@Table(name = "tb_refresh_token", schema = "auth")
public class RefreshTokenEntity {
    @Id
    private UUID id;

    private UUID userId;

    private String token;

    private Instant expiresAt;

    private Boolean revoked;

    private Instant createdAt;
}
