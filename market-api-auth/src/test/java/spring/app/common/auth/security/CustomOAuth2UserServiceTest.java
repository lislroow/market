package spring.app.common.auth.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import market.api.MainApplication;

//@SpringBootTest
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = MainApplication.class)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomOAuth2UserServiceTest {
  
  @Autowired
  OAuth2ClientProperties oauth2ClientProperties;
  
  @Autowired
  OAuth2UserService<OAuth2UserRequest, OAuth2User> userService;
  
  @Test
  public void test1() {
    
  }
  
  public void test() {
    org.springframework.security.oauth2.client.registration.ClientRegistration.Builder builder = 
        org.springframework.security.oauth2.client.registration.ClientRegistration.withRegistrationId("google");
    builder.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
    builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
    builder.redirectUri("{baseUrl}/{action}/oauth2/code/{registrationId}");
    builder.scope("openid", "profile", "email");
    builder.authorizationUri("https://accounts.google.com/o/oauth2/v2/auth");
    builder.tokenUri("https://www.googleapis.com/oauth2/v4/token");
    builder.jwkSetUri("https://www.googleapis.com/oauth2/v3/certs");
    builder.issuerUri("https://accounts.google.com");
    builder.userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo");
    builder.userNameAttributeName(IdTokenClaimNames.SUB);
    builder.clientName("Google");
    
    OAuth2ClientProperties.Registration properties = oauth2ClientProperties.getRegistration().get("google");
    
    org.springframework.boot.context.properties.PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
    map.from(properties::getClientId).to(builder::clientId);
    map.from(properties::getClientSecret).to(builder::clientSecret);
    map.from(properties::getClientAuthenticationMethod)
      .as(ClientAuthenticationMethod::new)
      .to(builder::clientAuthenticationMethod);
    map.from(properties::getAuthorizationGrantType)
      .as(AuthorizationGrantType::new)
      .to(builder::authorizationGrantType);
    map.from(properties::getRedirectUri).to(builder::redirectUri);
    map.from(properties::getScope).as(StringUtils::toStringArray).to(builder::scope);
    map.from(properties::getClientName).to(builder::clientName);
    
    ClientRegistration googleRegistration = builder.build();
    
    // change it: 'tokenValue'
    String tokenValue = ""; // change it
    Instant issuedAt = Instant.now();
    Instant expiresAt = issuedAt.plus(24l, ChronoUnit.HOURS);
    Set<String> scopes = Set.of("https://www.googleapis.com/auth/userinfo.profile", "https://www.googleapis.com/auth/userinfo.email", "openid");
    
    OAuth2AccessToken accessToken = new OAuth2AccessToken(
        TokenType.BEARER,
        tokenValue,
        issuedAt,
        expiresAt,
        scopes);
    
    // change it: id_token
    Map<String, Object> additionalParameters = Map.of("id_token", "");  // change it
    OAuth2User oauth2User = this.userService.loadUser(new OAuth2UserRequest(
        googleRegistration, accessToken, additionalParameters));
    
    System.out.println(oauth2User);
  }
}
