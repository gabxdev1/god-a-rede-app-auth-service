package br.com.gabxdev.infra.adapter.in.mapper;

import br.com.gabxdev.domain.model.AccessToken;
import br.com.gabxdev.domain.model.Profile;
import br.com.gabxdev.domain.model.User;
import br.com.gabxdev.infra.adapter.in.dto.SignUpPostRequest;
import br.com.gabxdev.infra.adapter.in.dto.TokenPostResponse;
import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.UUID;

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

    @Mappings({
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "firstName", source = "signUpPostRequest.firstName"),
            @Mapping(target = "lastName", source = "signUpPostRequest.lastName"),
            @Mapping(target = "bio", source = "signUpPostRequest.bio"),
            @Mapping(target = "avatarUrl", source = "signUpPostRequest.avatarUrl"),
            @Mapping(target = "username", source = "signUpPostRequest.username"),
            @Mapping(target = "createdAt", source = "createdAt"),
    })
    Profile toProfile(SignUpPostRequest signUpPostRequest, UUID userId, Instant createdAt);
}
