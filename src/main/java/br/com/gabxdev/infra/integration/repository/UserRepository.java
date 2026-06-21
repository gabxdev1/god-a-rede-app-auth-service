package br.com.gabxdev.infra.integration.repository;

import br.com.gabxdev.infra.integration.repository.entity.UserEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends ListCrudRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmailIgnoreCase(String email);
}
