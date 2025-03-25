package com.culturespot.culturespotdomain.core.performance.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.culturespot.culturespotdomain.core.performance.entity.Performance;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@ExtendWith(RandomBeansExtension.class)
class PerformanceRepositoryTest {

  @Autowired
  private PerformanceRepository performanceRepository;

  @Test
  void testSavePerformance(@Random Performance performance) {
    // Given: a random Performance object

    // When: saving the Performance object
    Performance savedPerformance = performanceRepository.save(performance);

    // Then: the saved Performance object should have a non-null ID
    assertNotNull(savedPerformance.getId());
  }

  @Test
  void testReadPerformance(@Random Performance performance) {
    // Given: a random Performance object that is saved
    Performance savedPerformance = performanceRepository.save(performance);

    // When: reading the Performance object by ID
    Optional<Performance> retrievedPerformance = performanceRepository.findById(
        savedPerformance.getId());

    // Then: the retrieved Performance object should match the saved one
    assertTrue(retrievedPerformance.isPresent());
    assertEquals(savedPerformance.getType(), retrievedPerformance.get().getType());
    assertEquals(savedPerformance.getCategory(), retrievedPerformance.get().getCategory());
    assertEquals(savedPerformance.getSeq(), retrievedPerformance.get().getSeq());
    assertEquals(savedPerformance.getTitle(), retrievedPerformance.get().getTitle());
    assertEquals(savedPerformance.getStartDate(), retrievedPerformance.get().getStartDate());
    assertEquals(savedPerformance.getEndDate(), retrievedPerformance.get().getEndDate());
    assertEquals(savedPerformance.getPlace(), retrievedPerformance.get().getPlace());
    assertEquals(savedPerformance.getAddress(), retrievedPerformance.get().getAddress());
    assertEquals(savedPerformance.getGpsX(), retrievedPerformance.get().getGpsX());
    assertEquals(savedPerformance.getGpsY(), retrievedPerformance.get().getGpsY());
    assertEquals(savedPerformance.getArea(), retrievedPerformance.get().getArea());
    assertEquals(savedPerformance.getPerformanceInfo().getThumbnail(),
        retrievedPerformance.get().getPerformanceInfo().getThumbnail());
    assertEquals(savedPerformance.getPerformanceInfo().getImageUrl(),
        retrievedPerformance.get().getPerformanceInfo().getImageUrl());
    assertEquals(savedPerformance.getPerformanceInfo().getDescription(),
        retrievedPerformance.get().getPerformanceInfo().getDescription());
    assertEquals(savedPerformance.getPerformanceInfo().getUrl(),
        retrievedPerformance.get().getPerformanceInfo().getUrl());
    assertEquals(savedPerformance.getPerformanceInfo().getPhone(),
        retrievedPerformance.get().getPerformanceInfo().getPhone());
    assertEquals(savedPerformance.getPerformanceInfo().getPrice(),
        retrievedPerformance.get().getPerformanceInfo().getPrice());
  }
}