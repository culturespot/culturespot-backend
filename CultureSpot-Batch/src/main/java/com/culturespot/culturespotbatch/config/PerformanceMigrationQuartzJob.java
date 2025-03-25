package com.culturespot.culturespotbatch.config;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

@RequiredArgsConstructor
public class PerformanceMigrationQuartzJob extends QuartzJobBean {

  private final JobLauncher jobLauncher;
  private final Job performanceMigrationBatchJob;

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    try {
      jobLauncher.run(performanceMigrationBatchJob, new JobParametersBuilder()
          .addLong("executed_timestamp", System.currentTimeMillis())
          .addLocalDate("target_date", LocalDate.now())
          .toJobParameters());
    } catch (Exception e) {
      throw new JobExecutionException(e);
    }
  }
}
