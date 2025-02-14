package com.culturespot.culturespotdomain.service;

import com.culturespot.culturespotcommon.exception.ExampleException;
import com.culturespot.culturespotdomain.entity.ExampleEntity;
import com.culturespot.culturespotdomain.repository.ExampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExampleDomainService {

	private final ExampleRepository exampleRepository;

	public void exception() {
		throw new ExampleException();
	}

	public ExampleEntity query(Long id) {
		return exampleRepository.findById(id).orElseThrow(ExampleException::new);
	}

	public ExampleEntity save(String content) {
		ExampleEntity build = ExampleEntity.builder().content(content).build();
		return exampleRepository.save(build);
	}
}