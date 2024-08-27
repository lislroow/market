package market.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import market.lib.constant.Constant;

@SpringBootApplication
@ComponentScan(basePackages = { Constant.BASE_PACKAGE })
@EnableWebSecurity
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = Constant.BASE_PACKAGE)
@MapperScan(basePackages = Constant.BASE_PACKAGE,
  annotationClass = org.apache.ibatis.annotations.Mapper.class
)
@EnableRedisRepositories
public class MainApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }
  
}
