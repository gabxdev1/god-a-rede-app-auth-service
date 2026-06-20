package br.com.gabxdev.infra.integration.repository;

import br.com.gabxdev.infra.integration.repository.entity.UserCredentialEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserCredentialRepository extends ListCrudRepository<UserCredentialEntity, UUID> {

    Optional<UserCredentialEntity> findByEmailIgnoreCase(String email);
}
