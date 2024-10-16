package market.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import market.api.config.JwtProperty;
import market.lib.constant.Constant;

@SpringBootApplication
@ComponentScan(basePackages = {Constant.BASE_PACKAGE})
@EnableWebSecurity
@EnableConfigurationProperties({OAuth2ClientProperties.class, JwtProperty.class})
public class AuthMain {
  
  public static void main(String[] args) {
    SpringApplication.run(AuthMain.class, args);
  }

}
