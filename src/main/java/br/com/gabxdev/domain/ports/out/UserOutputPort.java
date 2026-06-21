package br.com.gabxdev.domain.ports.out;

import br.com.gabxdev.domain.model.User;

import java.util.UUID;

public interface UserOutputPort {
    User save(User user);

    User findByEmailOrThrowNotFound(String email);

    User findByIdOrThrowNotFound(UUID id);
}
