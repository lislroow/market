package market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
    exclude = {RedisAutoConfiguration.class})
@EnableFeignClients
public class GatewayMain {
  
  public static void main(String[] args) {
    SpringApplication.run(GatewayMain.class, args);
  }
  
}
