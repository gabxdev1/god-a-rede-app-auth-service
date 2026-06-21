package br.com.gabxdev.infra.integration.repository.entity;

import br.com.gabxdev.domain.model.enums.UserStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Data
@Table(name = "tb_user", schema = "auth")
public class UserEntity {
    @Id
    private UUID id;

    private String email;

    private String passwordHash;

    private UserStatus status;

    private Boolean admin;

    private Instant createdAt;
}