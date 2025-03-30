package com.culturespot.culturespotbatch.job.performance;

import com.culturespot.culturespotdomain.core.performance.entity.Performance;
import com.culturespot.external.api.PerformanceDetailApiService;
import com.culturespot.external.api.dto.PerformanceDetailResponse;
import com.culturespot.external.api.dto.PerformanceDetailResponse.Item;
import com.culturespot.external.api.dto.PerformanceResponse;
import com.culturespot.external.api.dto.PerformanceResponse.Items;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Slf4j
@RequiredArgsConstructor
@Component
public class PerformanceProcessor implements
    ItemProcessor<PerformanceResponse.Items, List<Performance>> {

  private final PerformanceDetailApiService performanceDetailApiService;
  private final PerformanceMapper performanceMapper;

  private final ExecutorService executor = Executors.newFixedThreadPool(10);

  @Override
  public List<Performance> process(Items item) {
    log.info("Processor has started..., size: {}", item.getItems().size());

    List<Performance> performances = item.getItems()
        .stream()
        .map(it -> CompletableFuture.supplyAsync(() -> convert(it), executor))
        .map(CompletableFuture::join)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();

    log.info("Processor has completed!!!, size: {}", performances.size());
    return performances;
  }

  private Optional<Performance> convert(PerformanceResponse.Item item) {
    try {
      Optional<PerformanceDetailResponse> maybeDetailResponse = performanceDetailApiService.call(
          item.getSeq());
      if (maybeDetailResponse.isEmpty()) {
        return Optional.empty();
      }

      PerformanceDetailResponse detailResponse = maybeDetailResponse.get();
      if (detailResponse.getHeader() == null
          || !"00".equals(detailResponse.getHeader().getResultCode())
          || detailResponse.getBody() == null
          || detailResponse.getBody().getItems() == null
          || CollectionUtils.isEmpty(detailResponse.getBody().getItems().getItems())) {
        log.warn("Invalid detail response for item with seq: {}", item.getSeq());
        return Optional.empty();
      }

      Item detailItem = detailResponse.getBody().getItems().getItems()
          .get(0);

      return Optional.of(performanceMapper.toPerformance(item, detailItem));
    } catch (Exception e) {
      log.warn("Error calling performanceDetailApiService", e);
      return Optional.empty();
    }
  }
}
