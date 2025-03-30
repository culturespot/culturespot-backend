package com.culturespot.culturespotbatch.job.performance;

import com.culturespot.external.api.PerformancePeriodApiService;
import com.culturespot.external.api.dto.PerformanceResponse;
import com.culturespot.external.api.dto.PerformanceResponse.Items;
import java.time.LocalDate;
import java.util.Optional;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Slf4j
@RequiredArgsConstructor
@Component
public class PerformanceReader implements ItemReader<PerformanceResponse.Items> {

  private final PerformancePeriodApiService performancePeriodApiService;
  private JobExecution jobExecution;
  private Long pageNo;
  private Long numOfRow;
  private LocalDate from;
  @Nullable
  private LocalDate to;

  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
    this.jobExecution = stepExecution.getJobExecution();
    this.pageNo = stepExecution.getJobParameters().getLong("pageNo", 1L);
    this.numOfRow = stepExecution.getJobParameters().getLong("chunkSize", 100L);
    this.from = stepExecution.getJobParameters().getLocalDate("target_date", LocalDate.now());
    this.from = stepExecution.getJobParameters().getLocalDate("from", this.from);
    this.to = stepExecution.getJobParameters().getLocalDate("to", this.from.plusMonths(7L));

    log.info("Job Execution ID: {}", jobExecution.getId());
    log.info("Page Number: {}", pageNo);
    log.info("Number of Rows: {}", numOfRow);
    log.info("From Date: {}", from);
    log.info("To Date: {}", to);
  }

  @Override
  public Items read() {

    log.info("Calling API with Page Number: {}, Number of Rows: {}, From Date: {}, To Date: {}",
        pageNo, numOfRow, from, to);

    Optional<PerformanceResponse> maybeResponse = performancePeriodApiService.call(pageNo, numOfRow,
        from, to);

    if (maybeResponse.isEmpty()) {
      log.info("No response from API");
      return null;
    }

    PerformanceResponse response = maybeResponse.get();
    if (response.getHeader() == null
        || !"00".equals(response.getHeader().getResultCode())
        || response.getBody() == null
        || response.getBody().getItems() == null) {

      log.error("Invalid response from API: {}", response);
      throw new NonTransientResourceException("Invalid response from API");
    }

    log.info("After Call API, Total Count: {}, Page Number: {}, Number of Rows: {}",
        response.getBody().getTotalCount(), response.getBody().getPageNo(),
        response.getBody().getNumOfRows());
    PerformanceResponse.Items items = response.getBody().getItems();

    if (CollectionUtils.isEmpty(items.getItems())) {
      log.info("No items found in the response");
      return null;
    }

    log.info("Successfully retrieved items from API");

    pageNo += 1;

    return items;
  }
}
