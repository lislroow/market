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
import market.api.security.UsernamePasswordAuthenticationFailureHandler;
import market.api.security.UsernamePasswordAuthenticationSuccessHandler;
import market.api.security.LogoutHandlerImpl;
import market.api.security.LogoutSuccessHandlerImpl;
import market.api.security.SocialOAuth2LoginSuccessHandler;
import market.api.security.TokenService;
import market.api.security.UsernamePasswordDetailsService;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
  
  private final UsernamePasswordDetailsService usernamePasswordDetailsService;
  
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf((csrf) -> csrf.disable())
      .httpBasic((httpBasic) -> httpBasic.disable())
      .formLogin((formLogin) -> {
        formLogin
          .loginProcessingUrl("/auth/v1/login/process")
          .failureHandler(usernamePasswordAuthenticationFailureHandler())
          .successHandler(usernamePasswordAuthenticationSuccessHandler(tokenService));
      })
      .authenticationProvider(daoAuthenticationProvider())
      .authorizeHttpRequests((authorizeRequests) -> {
        authorizeRequests
          .requestMatchers("/auth/v1/login/process").permitAll()
          .requestMatchers("/auth/v1/session").permitAll()
          .requestMatchers("/auth/v1/token/**").permitAll() // TODO 임시 허용
          .requestMatchers("/actuator/**").permitAll()
          .requestMatchers("/error").permitAll()
          //.requestMatchers("/auth/v1/test").permitAll()
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
          .successHandler(socialOAuth2LoginSuccessHandler(tokenService))
      )
      .sessionManagement(sessionManagement -> {
        // spring-security 에서 세션을 생성하지 않고 사용도 하지 않도록 함
        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
      })
      .logout(logout -> { logout
        .logoutUrl("/auth/v1/logout")
        .logoutSuccessUrl("/")
        .addLogoutHandler(logoutHandlerImpl())
        .logoutSuccessHandler(logoutSuccessHandlerImpl())
        .deleteCookies("user");
      });
    ;
    return http.build();
  }
  
  @Bean
  DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(usernamePasswordDetailsService);
    return provider;
  }
  
  @Bean
  AuthenticationFailureHandler usernamePasswordAuthenticationFailureHandler() {
    AuthenticationFailureHandler handler = new UsernamePasswordAuthenticationFailureHandler();
    return handler;
  }
  
  @Autowired
  TokenService tokenService;
  
  @Bean
  AuthenticationSuccessHandler usernamePasswordAuthenticationSuccessHandler(TokenService tokenService) {
    AuthenticationSuccessHandler handler = new UsernamePasswordAuthenticationSuccessHandler(tokenService);
    return handler;
  }
  
  @Bean
  SocialOAuth2LoginSuccessHandler socialOAuth2LoginSuccessHandler(TokenService tokenService) {
    SocialOAuth2LoginSuccessHandler handler = new SocialOAuth2LoginSuccessHandler(tokenService);
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
  
  LogoutHandlerImpl logoutHandlerImpl() {
    LogoutHandlerImpl handler = new LogoutHandlerImpl();
    return handler;
  }
  
  @Bean
  LogoutSuccessHandlerImpl logoutSuccessHandlerImpl() {
    LogoutSuccessHandlerImpl handler = new LogoutSuccessHandlerImpl();
    return handler;
  }
}
