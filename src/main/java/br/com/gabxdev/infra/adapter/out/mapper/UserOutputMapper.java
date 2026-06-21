package br.com.gabxdev.infra.adapter.out.mapper;

import br.com.gabxdev.domain.model.User;
import br.com.gabxdev.infra.integration.repository.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserOutputMapper {
    UserEntity toEntity(User user);
    User toDomain(UserEntity userEntity);
}
