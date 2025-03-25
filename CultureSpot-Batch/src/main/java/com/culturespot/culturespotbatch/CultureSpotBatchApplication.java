package com.culturespot.culturespotbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableBatchProcessing
@SpringBootApplication(exclude = {BatchAutoConfiguration.class})
@ComponentScan(basePackages = {
		"com.culturespot.culturespotbatch",
		"com.culturespot.culturespotdomain",
})
public class CultureSpotBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(CultureSpotBatchApplication.class, args);
	}
}
