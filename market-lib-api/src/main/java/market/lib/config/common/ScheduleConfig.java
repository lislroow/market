package market.lib.config.common;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@Conditional(ScheduleProperties.class)
public class ScheduleConfig implements SchedulingConfigurer {

  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
    taskScheduler.setPoolSize(10);
    taskScheduler.setThreadNamePrefix("scheduler-");
    taskScheduler.initialize();
    taskRegistrar.setTaskScheduler(taskScheduler);
  }
}
