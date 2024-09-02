package market.lib.config.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ConfigurationProperties(prefix = "framework.swagger")
public class SwaggerProperties implements Condition {

  private final String ENABLED = "framework.swagger.enabled";
  private boolean enabled;
  
  @Override 
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    String enabled = context.getEnvironment().getProperty(ENABLED);
    log.trace("framework.swagger.enabled: {}", enabled);
    return "true".equals(enabled);
  }
  
  public boolean isEnabled() {
    return enabled;
  }
  
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
