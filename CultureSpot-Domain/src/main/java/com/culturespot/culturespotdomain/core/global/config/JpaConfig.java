package com.culturespot.culturespotdomain.core.global.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.culturespot.culturespotdomain.core")
@EnableJpaRepositories(basePackages = "com.culturespot.culturespotdomain.core")
@EnableJpaAuditing  // JPA Auditing 기능 활성화
public class JpaConfig {
}
