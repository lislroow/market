package market.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import lombok.extern.slf4j.Slf4j;

@ConfigurationProperties(prefix = "framework.common.scheduler")
@Slf4j
public class ScheduleProperties implements Condition {

  private static final String CONF_ENABLED = "framework.common.scheduler.enabled";
  private boolean enabled;
  
  @Override 
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    String value = context.getEnvironment().getProperty(CONF_ENABLED);
    log.trace("framework.common.scheduler.enabled: {}", value);
    return "true".equals(value);
  }
  
  public boolean isEnabled() {
    return enabled;
  }
  
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
