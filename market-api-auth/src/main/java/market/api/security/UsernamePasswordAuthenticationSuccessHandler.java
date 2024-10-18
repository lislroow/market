package market.api.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
public class UsernamePasswordAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final TokenService tokenService;
  
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    Assert.isTrue(authentication.getPrincipal() != null, "authentication.getPrincipal() is null");
    Assert.isTrue(authentication.getPrincipal() instanceof SessionUser, "authentication.getPrincipal() is not SessionUser type");
    
    try {
      ResponseCookie cookie = tokenService.createTokenCookie(authentication);
      response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
