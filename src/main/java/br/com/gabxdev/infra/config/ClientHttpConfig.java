package br.com.gabxdev.infra.config;

import br.com.gabxdev.infra.properties.HttpClientProps;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.concurrent.ExecutorService;

@Configuration
@RequiredArgsConstructor
public class ClientHttpConfig {

    private final ExecutorService virtualThreadPerTaskExecutor;

    @Bean
    HttpClient jdkHttpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(500))
                .executor(virtualThreadPerTaskExecutor)
                .followRedirects(HttpClient.Redirect.NEVER)
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    @Bean
    ClientHttpRequestFactory requestFactory(HttpClient jdkHttpClient) {
        JdkClientHttpRequestFactory jdkClientHttpRequestFactory = new JdkClientHttpRequestFactory(jdkHttpClient, virtualThreadPerTaskExecutor);

        jdkClientHttpRequestFactory.setReadTimeout(1000);

        return jdkClientHttpRequestFactory;
    }

    @Bean
    public RestClient userServiceRestClient(HttpClientProps props,
                                            ClientHttpRequestFactory requestFactory
    ) {
        return RestClient.builder()
                .requestFactory(requestFactory)
                .baseUrl(props.userService().urlBase())
                .build();
    }
}
