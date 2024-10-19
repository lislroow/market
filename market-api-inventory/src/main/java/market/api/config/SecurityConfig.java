package market.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import market.lib.config.webmvc.security.CustomHttp403ForbiddenEntryPoint;

@Configuration
public class SecurityConfig {
  
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .httpBasic(AbstractHttpConfigurer::disable)
      .formLogin(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(authorizeRequests -> 
        authorizeRequests
          .requestMatchers(HttpMethod.GET).permitAll()
          .anyRequest().permitAll()
      )
      .sessionManagement(sessionManagement -> 
        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
      )
      .exceptionHandling(exceptionHandling -> 
        exceptionHandling.authenticationEntryPoint(new CustomHttp403ForbiddenEntryPoint())
      )
    ;
    return http.build();
  }
}
