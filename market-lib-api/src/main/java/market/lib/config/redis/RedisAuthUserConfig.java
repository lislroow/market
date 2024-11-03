package market.lib.config.redis;

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

@Configuration
@ConditionalOnProperty(name = "spring.data.redis-auth-user.host", matchIfMissing = false)
public class RedisAuthUserConfig {

  @Value("${spring.data.redis-auth-user.host}")
  private String hostForRedisAuthUser;
  
  @Value("${spring.data.redis-auth-user.port}")
  private int portForRedisAuthUser;
  
  @Bean("redisConnectionFactoryForAuthUser")
  RedisConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory(hostForRedisAuthUser, portForRedisAuthUser);
  }
  
  @Bean("redisTemplateForAuthUser")
  RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());
    
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
    
    return redisTemplate;
  }
  
  
  @Bean("redisSupportForAuthUser")
  RedisSupport redisSupport(
      @Qualifier("redisTemplateForAuthUser") RedisTemplate<String, Object> redisTemplate,
      ModelMapper modelMapper) {
    return new RedisSupport(redisTemplate, modelMapper);
  }
  
}
