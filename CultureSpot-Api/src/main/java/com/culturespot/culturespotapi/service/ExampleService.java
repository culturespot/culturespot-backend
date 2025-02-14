package com.culturespot.culturespotapi.service;

import com.culturespot.culturespotcommon.dto.ExampleResponse;
import com.culturespot.culturespotdomain.entity.ExampleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExampleApiService {

	private final ExampleDomainService exampleDomainService;

	public ExampleResponse getExample() {
		ExampleEntity query = exampleDomainService.query(1L);
		return ExampleResponse.from(query);
	}

	public ExampleResponse createExample(){
		ExampleEntity asdf = exampleDomainService.save("asdf");
		return ExampleResponse.from(asdf);
	}
}