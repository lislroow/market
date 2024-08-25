package market.api.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientPropertiesMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import lombok.RequiredArgsConstructor;
import market.api.security.CustomAuthenticationFailureHandler;
import market.api.security.CustomAuthenticationSuccessHandler;
import market.api.security.CustomLogoutHandler;
import market.api.security.CustomLogoutSuccessHandler;
import market.api.security.CustomOAuth2LoginSuccessHandler;
import market.api.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(OAuth2ClientProperties.class)
@RequiredArgsConstructor
public class SecurityConfig {
  
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf((csrf) -> csrf.disable())
      .httpBasic((httpBasic) -> httpBasic.disable())
      .formLogin((formLogin) -> {
        formLogin
          .loginProcessingUrl("/auth/v1/login/process")
          .failureHandler(authenticationFailureHandler())
          .successHandler(authenticationSuccessHandler());
      })
      .authenticationProvider(daoAuthenticationProvider())
      .authorizeHttpRequests((authorizeRequests) -> {
        authorizeRequests
          .requestMatchers("/auth/v1/login/process").permitAll()
          .requestMatchers("/auth/v1/session").permitAll()
          .requestMatchers("/actuator/**").permitAll()
          .requestMatchers("/error").permitAll()
          .anyRequest().authenticated();
        }
      )
      .exceptionHandling((exceptionHandlingCustomizer) -> 
        exceptionHandlingCustomizer.authenticationEntryPoint(new Http403ForbiddenEntryPoint())
      )
      .oauth2Login((oauth2Login) ->
        oauth2Login
          .permitAll()
          .authorizationEndpoint((authorizationEndpointCustomizer) -> {
            authorizationEndpointCustomizer.authorizationRequestResolver(
              oauth2AuthorizationRequestResolver(clientRegistrationRepository())
            );
          })
          //.defaultSuccessUrl(String.format("http://%s", HOST), true)
          .successHandler(customOAuth2LoginSuccessHandler())
      )
      .sessionManagement(sessionManagement -> {
        sessionManagement
          .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
          //.sessionCreationPolicy(SessionCreationPolicy.NEVER);
      })
      .logout(logout -> { logout
        .logoutUrl("/auth/v1/logout")
        .logoutSuccessUrl("/")
        .addLogoutHandler(customLogoutHandler())
        .logoutSuccessHandler(customLogoutSuccessHandler())
        .deleteCookies("user");
      });
    ;
    return http.build();
  }
  
  @Autowired
  private UserDetailsServiceImpl userDetailsServiceImpl;
  
  @Bean
  DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsServiceImpl);
    return provider;
  }
  
  @Bean
  AuthenticationFailureHandler authenticationFailureHandler() {
    AuthenticationFailureHandler handler = new CustomAuthenticationFailureHandler();
    return handler;
  }
  
  @Bean
  AuthenticationSuccessHandler authenticationSuccessHandler() {
    AuthenticationSuccessHandler handler = new CustomAuthenticationSuccessHandler();
    return handler;
  }
  
  @Bean
  CustomOAuth2LoginSuccessHandler customOAuth2LoginSuccessHandler() {
    CustomOAuth2LoginSuccessHandler handler = new CustomOAuth2LoginSuccessHandler();
    return handler;
  }
  
  @Autowired
  OAuth2ClientProperties properties;
  
  @Bean
  ClientRegistrationRepository clientRegistrationRepository() {
    List<ClientRegistration> registrations = new ArrayList<>(
        new OAuth2ClientPropertiesMapper(properties).asClientRegistrations().values());
    return new InMemoryClientRegistrationRepository(registrations);
  }
  
  @Bean
  OAuth2AuthorizationRequestResolver oauth2AuthorizationRequestResolver(
      ClientRegistrationRepository clientRegistrationRepository) {
    String authorizationRequestBaseUri = 
        OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;
    return new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, authorizationRequestBaseUri);
  }
  
  CustomLogoutHandler customLogoutHandler() {
    CustomLogoutHandler handler = new CustomLogoutHandler();
    return handler;
  }
  
  @Bean
  CustomLogoutSuccessHandler customLogoutSuccessHandler() {
    CustomLogoutSuccessHandler handler = new CustomLogoutSuccessHandler();
    return handler;
  }
}
