package br.com.gabxdev;

import br.com.gabxdev.infra.properties.HttpClientProps;
import br.com.gabxdev.infra.properties.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.resilience.annotation.EnableResilientMethods;

@SpringBootApplication
@EnableConfigurationProperties({SecurityProperties.class, HttpClientProps.class})
@EnableResilientMethods
public class GodARedeAppAuthServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(GodARedeAppAuthServiceApplication.class, args);
	}

}
