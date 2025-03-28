package com.culturespot.culturespotdomain.core.performance.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PerformanceInfoConverter implements AttributeConverter<PerformanceInfo, String> {

  private final ObjectMapper objectMapper;

  public PerformanceInfoConverter() {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);
  }

  @Override
  public String convertToDatabaseColumn(PerformanceInfo performanceInfo) {
    try {
      return objectMapper.writeValueAsString(performanceInfo);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Error converting PerformanceInfo to JSON", e);
    }
  }

  @Override
  public PerformanceInfo convertToEntityAttribute(String dbData) {
    try {
      return objectMapper.readValue(dbData, PerformanceInfo.class);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Error converting JSON to PerformanceInfo", e);
    }
  }
}
