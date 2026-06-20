package br.com.gabxdev.infra.adapter.out;

import br.com.gabxdev.domain.exception.NotFoundException;
import br.com.gabxdev.domain.model.RefreshToken;
import br.com.gabxdev.domain.ports.out.RefreshTokenOutputPort;
import br.com.gabxdev.infra.adapter.out.mapper.RefreshTokenOutputMapper;
import br.com.gabxdev.infra.integration.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenAdapter implements RefreshTokenOutputPort {

    private final RefreshTokenOutputMapper mapper;
    private final RefreshTokenRepository repository;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        var entity = mapper.toEntity(refreshToken);
        var entitySaved = repository.save(entity);
        return mapper.toDomain(entitySaved);
    }

    @Override
    public RefreshToken findByTokenOrThrowNotFound(String token) {
        return repository.findByToken(token)
                .map(mapper::toDomain)
                .orElseThrow(() -> new NotFoundException("Refresh token not found with token: %s".formatted(token)));
    }
}
