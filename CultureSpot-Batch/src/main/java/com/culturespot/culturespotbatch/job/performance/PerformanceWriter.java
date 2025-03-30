package com.culturespot.culturespotbatch.job.performance;

import com.culturespot.culturespotdomain.core.performance.entity.Performance;
import com.culturespot.culturespotdomain.core.performance.repository.PerformanceRepository;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PerformanceWriter implements ItemWriter<List<Performance>> {

  private final PerformanceRepository performanceRepository;

  @Override
  public void write(Chunk<? extends List<Performance>> chunk) throws Exception {
    log.info("chunk: {}", chunk.size());
    log.info("size: {}", chunk.getItems().get(0).size());

    List<Performance> performances = chunk.getItems().get(0);
    Set<String> seqValues = performances.stream()
        .map(Performance::getSeq)
        .collect(Collectors.toSet());

    Map<String, Performance> existingPerformances = performanceRepository.findBySeqIn(seqValues)
        .stream()
        .collect(Collectors.toMap(Performance::getSeq, p -> p));

    List<Performance> performancesToSave = performances.stream()
        .map(performance -> {
          if (existingPerformances.containsKey(performance.getSeq())) {
            Performance existingPerformance = existingPerformances.get(performance.getSeq());
            existingPerformance.updateWith(performance);
            return existingPerformance;
          } else {
            return performance;
          }
        })
        .collect(Collectors.toList());

    performanceRepository.saveAll(performancesToSave);
  }
}