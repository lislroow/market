package market.api.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import market.lib.config.webmvc.security.CustomHttp403ForbiddenEntryPoint;

@Configuration
@EnableConfigurationProperties(OAuth2ClientProperties.class)
public class SecurityConfig {
  
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf((csrf) -> csrf.disable())
      .httpBasic((httpBasic) -> httpBasic.disable())
      .formLogin((formLogin) -> formLogin.disable())
      .authorizeHttpRequests((authorizeRequests) -> 
        authorizeRequests
          .requestMatchers(HttpMethod.GET).permitAll()
          .anyRequest().permitAll()
          //.anyRequest().hasAuthority("ROLE_USER")
      )
      .sessionManagement(sessionManagement -> {
        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
      })
      .exceptionHandling(exceptionHandling -> {
        exceptionHandling.authenticationEntryPoint(new CustomHttp403ForbiddenEntryPoint());
      })
    ;
    return http.build();
  }
  
  @Bean
  public RoleHierarchy roleHierarchy() {
      RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
      roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
      return roleHierarchy;
  }
}
