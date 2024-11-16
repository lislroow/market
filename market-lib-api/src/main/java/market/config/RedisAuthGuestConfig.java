package market.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import market.common.redis.RedisSupport;

@Configuration
@ConditionalOnProperty(name = "spring.data.redis-auth-guest.host", matchIfMissing = false)
public class RedisAuthGuestConfig {

  @Value("${spring.data.redis-auth-guest.host}")
  private String hostForRedisAuthGuest;
  
  @Value("${spring.data.redis-auth-guest.port}")
  private int portForRedisAuthGuest;
  
  @Bean("redisConnectionFactoryForAuthGuest")
  RedisConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory(hostForRedisAuthGuest, portForRedisAuthGuest);
  }
  
  @Bean("redisTemplateForAuthGuest")
  RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());
    
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
    
    return redisTemplate;
  }
  
  
  @Bean("redisSupportForAuthGuest")
  RedisSupport redisSupport(
      @Qualifier("redisTemplateForAuthGuest") RedisTemplate<String, Object> redisTemplate,
      ModelMapper modelMapper) {
    return new RedisSupport(redisTemplate, modelMapper);
  }
  
}
