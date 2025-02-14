package com.culturespot.culturespotcommon.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExampleResponse {

	private final Long id;
	private final String content;

	public static ExampleResponse from(ExampleEntity exampleEntity) {
		return new ExampleResponse(exampleEntity.getId(), exampleEntity.getContent());
	}
}
