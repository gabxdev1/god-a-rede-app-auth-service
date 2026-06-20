package br.com.gabxdev.domain.model;

import br.com.gabxdev.domain.model.enums.UserStatus;

import java.time.Instant;
import java.util.UUID;

public class UserCredential {
    private UUID id;

    private String email;

    private String passwordHash;

    private UserStatus status;

    private Boolean admin;

    private Instant createdAt;

    public UserCredential() {
    }

    public UserCredential(UUID id, String email, String passwordHash, UserStatus status, Boolean admin, Instant createdAt) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.status = status;
        this.admin = admin;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
