package com.culturespot.culturespotbatch.job.performance;

import com.culturespot.culturespotdomain.core.performance.entity.Performance;
import com.culturespot.culturespotdomain.core.performance.repository.PerformanceRepository;
import com.culturespot.external.api.PerformanceDetailApiService;
import com.culturespot.external.api.PerformancePeriodApiService;
import com.culturespot.external.api.dto.PerformanceResponse;
import com.culturespot.external.config.ExternalApiConfig;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@RequiredArgsConstructor
@Import(ExternalApiConfig.class)
@Configuration
public class PerformanceMigrationBatch {

  private final PlatformTransactionManager platformTransactionManager;
  private final JobRepository jobRepository;

  private final PerformancePeriodApiService performancePeriodApiService;
  private final PerformanceDetailApiService performanceDetailApiService;
  private final PerformanceMapper performanceMapper;
  private final PerformanceRepository performanceRepository;

  private final PerformanceMigrationJobListener performanceMigrationJobListener;

  @Bean
  public Job performanceMigrationBatchJob() {
    return new JobBuilder("PERFORMANCE_MIGRATION_BATCH_JOB", jobRepository)
        .listener(performanceMigrationJobListener)
        .start(performanceStep())
        .build();
  }

  @Bean
  @JobScope
  public Step performanceStep() {
    return new StepBuilder("performanceStep", jobRepository)
        .<PerformanceResponse.Items, List<Performance>>chunk(1, platformTransactionManager)
        .reader(performanceReader())
        .processor(performanceProcessor())
        .writer(performanceWriter())
        .build();
  }

  @Bean
  @StepScope
  public PerformanceReader performanceReader() {
    return new PerformanceReader(performancePeriodApiService);
  }

  @Bean
  @StepScope
  public PerformanceProcessor performanceProcessor() {
    return new PerformanceProcessor(performanceDetailApiService, performanceMapper);
  }

  @Bean
  @StepScope
  public PerformanceWriter performanceWriter() {
    return new PerformanceWriter(performanceRepository);
  }
}
