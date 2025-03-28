package com.culturespot.culturespotbatch.job.performance;

import com.culturespot.culturespotdomain.common.DiscordMessageService;
import com.culturespot.culturespotdomain.common.DiscordMessageService.Color;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PerformanceMigrationJobListener implements JobExecutionListener {

  private final DiscordMessageService discordMessageService;

  @Override
  public void beforeJob(JobExecution jobExecution) {
    String jobName = jobExecution.getJobInstance().getJobName();
    log.info("Starting job: {}", jobName);
    JobParameters jobParameters = jobExecution.getJobParameters();
    log.info("Job parameters: {}", jobParameters);
    discordMessageService.send("Job started...", "Job " + jobName + " has started.", Color.INFO);
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    String jobName = jobExecution.getJobInstance().getJobName();
    log.info("Completed job: {}", jobName);
    JobParameters jobParameters = jobExecution.getJobParameters();
    log.info("Job parameters: {}", jobParameters);
    BatchStatus status = jobExecution.getStatus();
    log.info("Job status: {}", status);

    String description =
        "Job " + jobName + " has completed with status: " + status + ". Parameters: "
            + jobParameters;

    if (status.equals(BatchStatus.COMPLETED)) {
      discordMessageService.send("Job completed!!!", description, Color.INFO);
      return;
    }
    discordMessageService.send("Job filed...", description, Color.WARN);
  }
}