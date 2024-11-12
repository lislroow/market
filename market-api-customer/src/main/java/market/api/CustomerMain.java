package market.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import market.lib.constant.Constant;

@SpringBootApplication
@ComponentScan(basePackages = Constant.BASE_PACKAGE)
@EnableWebSecurity
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = Constant.BASE_PACKAGE)
@EnableFeignClients(basePackages = Constant.BASE_PACKAGE)
public class CustomerMain {
  
  public static void main(String[] args) {
    SpringApplication.run(CustomerMain.class, args);
  }

}
