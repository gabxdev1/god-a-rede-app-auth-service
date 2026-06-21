package br.com.gabxdev.infra.adapter.out;

import br.com.gabxdev.domain.model.Profile;
import br.com.gabxdev.domain.ports.out.ProfileOutputPort;
import br.com.gabxdev.infra.properties.HttpClientProps;
import lombok.RequiredArgsConstructor;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class ProfileAdapter implements ProfileOutputPort {

    private final RestClient userServiceRestClient;

    private final HttpClientProps props;


    @Override
    @Retryable(
            excludes = {
                    HttpClientErrorException.BadRequest.class,
                    HttpClientErrorException.Unauthorized.class,
                    HttpClientErrorException.Forbidden.class,
                    HttpClientErrorException.NotFound.class
            },
            maxRetries = 4,
            delay = 700,
            multiplier = 2.0
    )
    public void criarProfile(Profile profile) {
        var path = props.userService().endpoints().criarProfile();

        userServiceRestClient.post()
                .uri(path)
                .body(profile)
                .retrieve()
                .toBodilessEntity();
    }
}
