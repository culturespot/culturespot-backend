package com.culturespot.culturespotdomain.core.performance.entity;

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
}
