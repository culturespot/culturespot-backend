package com.culturespot.culturespotdomain.core.performance.entity;

import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum Category {

  EXHIBITION("전시"),
  THEATER("연극"),
  MUSIC("음악"),
  DANCING("무용"),
  ART("미술"),
  BUILDING("건축"),
  VIDEO("영상"),
  LITERATURE("문학"),
  CULTURAL("문화정책"),
  FIESTA("축제"),
  ETC("기타");

  private final String name;

  Category(String name) {
    this.name = name;
  }

  public static Category fromName(String name) {
    for (Category category : values()) {
      if (category.name.equalsIgnoreCase(name)) {
        return category;
      }
    }
    return ETC;
  }

  public static Category fromString(String value) {
    return Arrays.stream(Category.values())
            .filter(category -> category.getName().equals(value))
            .findFirst()
            .orElse(Category.ETC);
  }

  private static final Set<String> VALID_NAMES = Arrays.stream(values())
          .map(Category::getName)
          .collect(Collectors.toSet());
}
