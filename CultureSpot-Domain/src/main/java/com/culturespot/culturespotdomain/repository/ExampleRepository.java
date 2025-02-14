package com.culturespot.culturespotdomain.repository;

import com.culturespot.culturespotdomain.entity.ExampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExampleRepository extends JpaRepository<ExampleEntity, Long> {}