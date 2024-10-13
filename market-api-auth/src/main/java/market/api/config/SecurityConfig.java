package market.api.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientPropertiesMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
import market.api.security.TokenService;
import market.api.security.UserDetailsServiceImpl;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
  
  private final UserDetailsServiceImpl userDetailsServiceImpl;
  
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf((csrf) -> csrf.disable())
      .httpBasic((httpBasic) -> httpBasic.disable())
      .formLogin((formLogin) -> {
        formLogin
          .loginProcessingUrl("/auth/v1/login/process")
          .failureHandler(authenticationFailureHandler())
          .successHandler(authenticationSuccessHandler(tokenService));
      })
      .authenticationProvider(daoAuthenticationProvider())
      .authorizeHttpRequests((authorizeRequests) -> {
        authorizeRequests
          .requestMatchers("/auth/v1/login/process").permitAll()
          .requestMatchers("/auth/v1/session").permitAll()
          .requestMatchers("/auth/v1/token/**").permitAll() // TODO 임시 허용
          .requestMatchers("/actuator/**").permitAll()
          .requestMatchers("/error").permitAll()
          .requestMatchers("/auth/v1/test").permitAll()
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
          .successHandler(customOAuth2LoginSuccessHandler(tokenService))
      )
      .sessionManagement(sessionManagement -> {
        // spring-security 에서 세션을 생성하지 않고 사용도 하지 않도록 함
        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
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
  
  @Autowired
  TokenService tokenService;
  
  @Bean
  AuthenticationSuccessHandler authenticationSuccessHandler(TokenService tokenService) {
    AuthenticationSuccessHandler handler = new CustomAuthenticationSuccessHandler(tokenService);
    return handler;
  }
  
  @Bean
  CustomOAuth2LoginSuccessHandler customOAuth2LoginSuccessHandler(TokenService tokenService) {
    CustomOAuth2LoginSuccessHandler handler = new CustomOAuth2LoginSuccessHandler(tokenService);
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
