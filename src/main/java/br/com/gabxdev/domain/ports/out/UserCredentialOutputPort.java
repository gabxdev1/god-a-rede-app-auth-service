package br.com.gabxdev.domain.ports.out;

import br.com.gabxdev.domain.model.UserCredential;

import java.util.UUID;

public interface UserCredentialOutputPort {
    UserCredential save(UserCredential userCredential);

    UserCredential findByEmailOrThrowNotFound(String email);

    UserCredential findByIdOrThrowNotFound(UUID id);
}
