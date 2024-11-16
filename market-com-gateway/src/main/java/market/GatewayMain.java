package market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import market.common.constant.Constant;

@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@ComponentScan(basePackages = { Constant.BASE_PACKAGE })
@EnableFeignClients(basePackages = { Constant.BASE_PACKAGE })
public class GatewayMain {
  
  public static void main(String[] args) {
    SpringApplication.run(GatewayMain.class, args);
  }
  
}
