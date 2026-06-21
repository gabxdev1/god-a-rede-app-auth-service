package br.com.gabxdev.infra.adapter.out;

import br.com.gabxdev.domain.exception.NotFoundException;
import br.com.gabxdev.domain.model.User;
import br.com.gabxdev.domain.ports.out.UserOutputPort;
import br.com.gabxdev.infra.adapter.out.mapper.UserOutputMapper;
import br.com.gabxdev.infra.integration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserAdapter implements UserOutputPort {
    private final UserRepository repository;
    private final UserOutputMapper mapper;

    @Override
    public User save(User user) {
        var entity = mapper.toEntity(user);
        var saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public User findByEmailOrThrowNotFound(String email) {
        return repository.findByEmailIgnoreCase(email)
                .map(mapper::toDomain)
                .orElseThrow(() -> new NotFoundException("User not found with email: %s".formatted(email)));
    }

    @Override
    public User findByIdOrThrowNotFound(UUID id) {
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new NotFoundException("User not found with id: %s".formatted(id)));
    }
}
