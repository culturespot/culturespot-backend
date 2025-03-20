package com.culturespot.culturespotserviceapi;

import com.culturespot.culturespotdomain.core.global.config.DomainModuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.culturespot.culturespotdomain.core")
@EnableJpaRepositories(basePackages = "com.culturespot.culturespotdomain.core")
@Import(DomainModuleConfig.class)
public class CultureSpotServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CultureSpotServiceApiApplication.class, args);
	}

}
