package br.com.gabxdev.application;

import br.com.gabxdev.domain.model.Profile;
import br.com.gabxdev.domain.ports.in.ProfileInboundPort;
import br.com.gabxdev.domain.ports.out.ProfileOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProfileUseCase implements ProfileInboundPort {

    private final ProfileOutputPort profileOutputPort;

    @Override
    public void criarProfile(Profile profile) {
        log.info("Creating user profile id: {}", profile.userId());
        profileOutputPort.criarProfile(profile);
        log.info("User profile created successfully id: {}", profile.userId());
    }
}
