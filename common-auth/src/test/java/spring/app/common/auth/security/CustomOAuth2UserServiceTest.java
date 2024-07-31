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

import spring.MainApplication;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MainApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomOAuth2UserServiceTest {
  
  @Autowired
  OAuth2ClientProperties oauth2ClientProperties;
  
  @Autowired
  OAuth2UserService<OAuth2UserRequest, OAuth2User> userService;
  
  @Test
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
    String tokenValue = "ya29.a0AXooCgsI6MHdEIzkstbMou_NBglmseoIoSCFrzPqxcNRutuz8y4CVDgv9eorTfELLjb_wNk64hrCQ2Uxj6rm7_nNGWkMaHpsZ3dDP0BC_dpsOCBw5ftds4rdaFjkrAQec3DpN3FeurIFDLGWopmVAwyhA_wzaKImTYcaCgYKAWASARISFQHGX2MijmH3uKb0mzJpVAl9v1Gu2w0170"; // change it
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
    Map<String, Object> additionalParameters = Map.of("id_token", "eyJhbGciOiJSUzI1NiIsImtpZCI6IjY3MTk2NzgzNTFhNWZhZWRjMmU3MDI3NGJiZWE2MmRhMmE4YzRhMTIiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIxMjU2NjA1Mjk4OTktNzcwdWJqdWR0bGJnMGZncTQ3cTc2OGt1NW4wM3VrdXAuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIxMjU2NjA1Mjk4OTktNzcwdWJqdWR0bGJnMGZncTQ3cTc2OGt1NW4wM3VrdXAuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDg1NjgxNTMzODY5MDM0MDg3NDkiLCJlbWFpbCI6Im1na2ltLm5ldEBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXRfaGFzaCI6IjNxTkpPcklRYnlUMXFpZXJscG1Ib2ciLCJuYW1lIjoi66y066qF7JmVIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FDZzhvY0xJY3paNDM4NEFfakpVS0Z5MnUzeWc3Si1FR3d3d1JqZGtnZnJIb1E3VF9IMkVsU1U9czk2LWMiLCJnaXZlbl9uYW1lIjoi66y066qF7JmVIiwiaWF0IjoxNzE3MDQ4OTE4LCJleHAiOjE3MTcwNTI1MTh9.Mh1WHL3wFJJg0tVvdljhVnf6l8EqhKUx1yOOdAJsIg61T6K4cuDyCVCtZUniAiKozyaFn6awXcQcom2BAM5saXEGrhyeAuQ1WEGeacmXYYJ1K4dGtC6eCtYEBIIGIblMMGJ6cKFLVlXquvL_V5aZtSl7XoFNotjjBX5rpMRtzzvXwPrdkeZyltX9Wty1BIoAi8cBa-SLNYMnOPfmvL1vJD7eLfqSO3u5qNWspOaqP5_njuSkGbScXNcU1RqI_-nwsRetFuoQaVitG1hKVoNnpP5X-XOS2rEqfbghDp2cdlItquB3ip9NJt1Rs-vW-8HVkVODXALb9UEXs89D2p4OAw");  // change it
    OAuth2User oauth2User = this.userService.loadUser(new OAuth2UserRequest(
        googleRegistration, accessToken, additionalParameters));
    
    System.out.println(oauth2User);
  }
}
