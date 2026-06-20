package br.com.gabxdev.domain.model;

import java.time.Instant;
import java.util.UUID;

public class RefreshToken {
    private UUID id;

    private UUID userId;

    private String token;

    private Instant expiresAt;

    private Boolean revoked;

    private Instant createdAt;


    public RefreshToken() {
    }

    public RefreshToken(UUID id, UUID userId, String token, Instant expiresAt, Boolean revoked, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.expiresAt = expiresAt;
        this.revoked = revoked;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Boolean getRevoked() {
        return revoked;
    }

    public void setRevoked(Boolean revoked) {
        this.revoked = revoked;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
