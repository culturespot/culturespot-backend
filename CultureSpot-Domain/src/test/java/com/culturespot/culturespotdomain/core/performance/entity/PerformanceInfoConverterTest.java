package com.culturespot.culturespotdomain.core.performance.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(RandomBeansExtension.class)
class PerformanceInfoConverterTest {

  private PerformanceInfoConverter converter;

  @BeforeEach
  void setUp() {
    converter = new PerformanceInfoConverter();
  }

  @Test
  void convertToDatabaseColumn_normalFlow(@Random PerformanceInfo performanceInfo) {

    String json = converter.convertToDatabaseColumn(performanceInfo);
    assertNotNull(json);
    assertTrue(json.contains("\"thumbnail\":\"" + performanceInfo.getThumbnail() + "\""));
    assertTrue(json.contains("\"imageUrl\":\"" + performanceInfo.getImageUrl() + "\""));
    assertTrue(json.contains("\"description\":\"" + performanceInfo.getDescription() + "\""));
    assertTrue(json.contains("\"url\":\"" + performanceInfo.getUrl() + "\""));
    assertTrue(json.contains("\"phone\":\"" + performanceInfo.getPhone() + "\""));
    assertTrue(json.contains("\"price\":\"" + performanceInfo.getPrice() + "\""));
  }

  @Test
  void convertToEntityAttribute_normalFlow(@Random PerformanceInfo performanceInfo) {
    String json = converter.convertToDatabaseColumn(performanceInfo);
    PerformanceInfo result = converter.convertToEntityAttribute(json);
    assertNotNull(result);
    assertEquals(performanceInfo.getThumbnail(), result.getThumbnail());
    assertEquals(performanceInfo.getImageUrl(), result.getImageUrl());
    assertEquals(performanceInfo.getDescription(), result.getDescription());
    assertEquals(performanceInfo.getUrl(), result.getUrl());
    assertEquals(performanceInfo.getPhone(), result.getPhone());
    assertEquals(performanceInfo.getPrice(), result.getPrice());
  }

  @Test
  void convertToDatabaseColumn_exceptionFlow() {
    PerformanceInfo performanceInfo = mock(PerformanceInfo.class);
    when(performanceInfo.getThumbnail()).thenThrow(new RuntimeException("Test exception"));

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertToDatabaseColumn(performanceInfo));
  }

  @Test
  void convertToEntityAttribute_exceptionFlow() {
    String invalidJson = "invalid json";

    assertThrows(IllegalArgumentException.class,
        () -> converter.convertToEntityAttribute(invalidJson));
  }
}