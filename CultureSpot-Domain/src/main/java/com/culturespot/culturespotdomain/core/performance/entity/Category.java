package com.culturespot.culturespotdomain.core.performance.entity;

import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum Category {
  THEATER("A000", "연극"),
  MUSIC_CONCERT("B000", "음악/콘서트"),
  KOREAN_MUSIC("B002", "국악"),
  DANCING_BALLET("C000", "무용/발레"),
  EXHIBITION("D000", "전시"),
  MUSICAL_OPERA("B003", "뮤지컬/오페라"),
  CHILDREN_FAMILY("E000", "아동/가족"),
  EVENT_FESTIVAL("F000", "행사/축제"),
  EDU_EXPERIENCE("G000", "교육/체험"),
  BOOKS("H000", "도서"),
  SPORTS("I000", "체육"),
  ETC("L000", "기타");

  private final String code;
  private final String name;

  Category(String code, String name) {
    this.code = code;
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
