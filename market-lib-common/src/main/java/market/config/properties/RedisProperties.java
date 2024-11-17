package market.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import market.common.enumcode.REDIS_TYPE;

@ConfigurationProperties(prefix = "market.redis", ignoreUnknownFields = true)
@Data
public class RedisProperties {
  
  private Configure authGuest;
  private Configure authUser;
  
  public Configure getConfigure(REDIS_TYPE redisType) {
    switch (redisType) {
    case AUTH_GUEST:
      return authGuest;
    case AUTH_USER:
      return authUser;
    }
    return null;
  }
  
  @Data
  public static class Configure {
    private Boolean enabled;
    private String host;
    private Integer port;
  }
}