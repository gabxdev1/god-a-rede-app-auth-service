package br.com.gabxdev.infra.integration.repository;

import br.com.gabxdev.infra.integration.repository.entity.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, UUID> {
    Optional<RefreshTokenEntity> findByToken(String token);
}
