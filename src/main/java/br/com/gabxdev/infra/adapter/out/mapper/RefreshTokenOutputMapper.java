package br.com.gabxdev.infra.adapter.out.mapper;

import br.com.gabxdev.domain.model.RefreshToken;
import br.com.gabxdev.infra.integration.repository.entity.RefreshTokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RefreshTokenOutputMapper {
    RefreshToken toDomain(RefreshTokenEntity refreshTokenEntity);

    RefreshTokenEntity toEntity(RefreshToken refreshToken);
}
