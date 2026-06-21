package br.com.gabxdev.infra.adapter.in.mapper;

import br.com.gabxdev.domain.model.AccessToken;
import br.com.gabxdev.domain.model.User;
import br.com.gabxdev.infra.adapter.in.dto.SignUpPostRequest;
import br.com.gabxdev.infra.adapter.in.dto.TokenPostResponse;
import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthInboundMapper {

    @Mappings({
            @Mapping(target = "passwordHash", expression = "java(passwordEncoder.encode(signUpPostRequest.password()))"),
            @Mapping(target = "status", expression = "java(br.com.gabxdev.domain.model.enums.UserStatus.ACTIVE)"),
            @Mapping(target = "admin", expression = "java(java.lang.Boolean.FALSE)"),
            @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    })
    User toUserCredential(SignUpPostRequest signUpPostRequest, @Context PasswordEncoder passwordEncoder);

    TokenPostResponse toTokenPostResponse(AccessToken accessToken);
}
