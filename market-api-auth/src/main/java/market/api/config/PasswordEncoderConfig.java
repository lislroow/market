package market.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

  @Bean("bcryptPasswordEncoder")
  BCryptPasswordEncoder bcryptPasswordEncoder(){
    return new BCryptPasswordEncoder();
  }
}
