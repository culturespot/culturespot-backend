package com.culturespot.culturespotdomain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "tbl_example")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExampleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String content;

	@Builder
	public ExampleEntity(String content) {
		this.content = content;
	}
}
