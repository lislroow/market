package market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import market.common.constant.Constant;

@SpringBootApplication
@ComponentScan(basePackages = { Constant.BASE_PACKAGE })
@EnableWebSecurity
public class InventoryMain {
  
  public static void main(String[] args) {
    SpringApplication.run(InventoryMain.class, args);
  }
  
}
