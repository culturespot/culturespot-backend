package com.culturespot.culturespotdomain.core.comment.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class LikedUserIdsConverter implements AttributeConverter<Set<Long>, String> {

  @Override
  public String convertToDatabaseColumn(Set<Long> attribute) {
    if (attribute == null || attribute.isEmpty()) {
      return "";
    }
    return attribute.stream()
        .map(String::valueOf)
        .collect(Collectors.joining(","));
  }

  @Override
  public Set<Long> convertToEntityAttribute(String dbData) {
    if (dbData == null || dbData.isEmpty()) {
      return Set.of();
    }
    return Arrays.stream(dbData.split(","))
        .map(Long::valueOf)
        .collect(Collectors.toSet());
  }
}