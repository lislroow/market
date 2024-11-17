package market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(
    exclude = { SecurityAutoConfiguration.class })
@EnableTransactionManagement
@EnableJpaRepositories
@EnableFeignClients
public class CustomerMain {
  
  public static void main(String[] args) {
    SpringApplication.run(CustomerMain.class, args);
  }

}
