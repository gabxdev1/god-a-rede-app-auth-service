package br.com.gabxdev.infra.adapter.out;

import br.com.gabxdev.domain.exception.NotFoundException;
import br.com.gabxdev.domain.model.UserCredential;
import br.com.gabxdev.domain.ports.out.UserCredentialOutputPort;
import br.com.gabxdev.infra.adapter.out.mapper.UserCredentialOutputMapper;
import br.com.gabxdev.infra.integration.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserCredentialAdapter implements UserCredentialOutputPort {
    private final UserCredentialRepository repository;
    private final UserCredentialOutputMapper mapper;

    @Override
    public UserCredential save(UserCredential userCredential) {
        var entity = mapper.toEntity(userCredential);
        var saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public UserCredential findByEmailOrThrowNotFound(String email) {
        return repository.findByEmailIgnoreCase(email)
                .map(mapper::toDomain)
                .orElseThrow(() -> new NotFoundException("User not found with email: %s".formatted(email)));
    }

    @Override
    public UserCredential findByIdOrThrowNotFound(UUID id) {
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new NotFoundException("User not found with id: %s".formatted(id)));
    }
}
