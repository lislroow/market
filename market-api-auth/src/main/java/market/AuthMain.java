package market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import market.config.properties.JwtProperties;

@SpringBootApplication(exclude = { RedisAutoConfiguration.class })
@EnableWebSecurity
@EnableTransactionManagement
@EnableConfigurationProperties({OAuth2ClientProperties.class, JwtProperties.class})
public class AuthMain {
  
  public static void main(String[] args) {
    SpringApplication.run(AuthMain.class, args);
  }

}
