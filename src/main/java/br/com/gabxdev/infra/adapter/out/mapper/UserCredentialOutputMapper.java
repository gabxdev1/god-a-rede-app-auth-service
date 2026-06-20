package br.com.gabxdev.infra.adapter.out.mapper;

import br.com.gabxdev.domain.model.UserCredential;
import br.com.gabxdev.infra.integration.repository.entity.UserCredentialEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserCredentialOutputMapper {
    UserCredentialEntity toEntity(UserCredential userCredential);
    UserCredential toDomain(UserCredentialEntity userCredentialEntity);
}
