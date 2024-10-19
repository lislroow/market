package market.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

import market.lib.constant.Constant;

@SpringBootApplication
@ComponentScan(basePackages = { Constant.BASE_PACKAGE })
@EnableEurekaServer
public class EurekaMain {
  
  public static void main(String[] args) {
    SpringApplication.run(EurekaMain.class, args);
  }
  
}
