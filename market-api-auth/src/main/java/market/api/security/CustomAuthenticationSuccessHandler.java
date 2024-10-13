package market.api.security;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.Assert;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import market.lib.vo.SessionUser;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final TokenService tokenService;
  
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    Assert.isTrue(authentication.getPrincipal() != null, "authentication.getPrincipal() is null");
    Assert.isTrue(authentication.getPrincipal() instanceof SessionUser, "authentication.getPrincipal() is not SessionUser type");
    String userId = ((SessionUser)authentication.getPrincipal()).getEmail();
    
    try {
      //String tokenId = tokenService.createToken(userId);
      //MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
      //MediaType jsonMimeType = MediaType.APPLICATION_JSON;
      //Map<String, String> message = Map.of("token", tokenId);
      //if (converter.canWrite(message.getClass(), jsonMimeType)) {
      //    converter.write(message, jsonMimeType, new ServletServerHttpResponse(response));
      //}
      ResponseCookie cookie = tokenService.createTokenCookie(userId);
      response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
