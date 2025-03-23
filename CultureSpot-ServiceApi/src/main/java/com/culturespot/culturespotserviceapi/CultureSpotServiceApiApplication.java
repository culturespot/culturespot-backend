package com.culturespot.culturespotserviceapi;

import com.culturespot.culturespotdomain.core.global.config.DomainModuleConfig;
import com.culturespot.culturespotdomain.core.global.config.JpaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
		DomainModuleConfig.class,
		JpaConfig.class
})
public class CultureSpotServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CultureSpotServiceApiApplication.class, args);
	}

}
