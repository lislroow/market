package market.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import market.lib.constant.Constant;

@SpringBootApplication
@ComponentScan(basePackages = { Constant.BASE_PACKAGE })
@EnableWebSecurity
public class DeliveryMain {
  
  public static void main(String[] args) {
    SpringApplication.run(DeliveryMain.class, args);
  }
  
}
