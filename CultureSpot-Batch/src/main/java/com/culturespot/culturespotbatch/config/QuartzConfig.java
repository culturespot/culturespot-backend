package com.culturespot.culturespotbatch.config;

import java.util.Objects;
import org.quartz.CronTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {

  @Value("${performance.migration.job.cron.schedule:0 */5 * * * ?}")
  private String performanceMigrationJobCronSchedule;

  @Autowired
  private AutowireCapableBeanFactory beanFactory;

  @Bean
  public JobDetailFactoryBean performanceMigrationJobDetailFactory() {
    JobDetailFactoryBean factory = new JobDetailFactoryBean();
    factory.setJobClass(PerformanceMigrationQuartzJob.class);
    factory.setName("PerformanceMigrationQuartzJob");
    factory.setDescription("Invoke performance migration job...");
    factory.setDurability(true);
    return factory;
  }

  @Bean
  public CronTriggerFactoryBean performanceJobTrigger(
      JobDetailFactoryBean performanceMigrationJobDetailFactory) {
    CronTriggerFactoryBean triggerFactoryBean = new CronTriggerFactoryBean();
    triggerFactoryBean.setJobDetail(
        Objects.requireNonNull(performanceMigrationJobDetailFactory.getObject()));
    triggerFactoryBean.setName("CronTrigger");

    String cronExpression = System.getProperty("performance.migration.job.cron.schedule",
        performanceMigrationJobCronSchedule);
    triggerFactoryBean.setCronExpression(cronExpression);
    triggerFactoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);

    return triggerFactoryBean;
  }

  @Bean
  public SchedulerFactoryBean schedulerFactory(CronTriggerFactoryBean performanceJobTrigger) {
    SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
    schedulerFactory.setTriggers(performanceJobTrigger.getObject());
    schedulerFactory.setJobFactory(new AutowiringSpringBeanJobFactory(beanFactory));
    schedulerFactory.setAutoStartup(true);
    return schedulerFactory;
  }
}
