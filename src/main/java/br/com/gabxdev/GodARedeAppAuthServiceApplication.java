package br.com.gabxdev;

import br.com.gabxdev.infra.properties.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({SecurityProperties.class})

public class GodARedeAppAuthServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(GodARedeAppAuthServiceApplication.class, args);
	}

}
