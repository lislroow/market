package market.api.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.StringUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    String redirectUri = request.getParameter("redirect_uri");
    if (!StringUtils.hasLength(redirectUri)) {
      redirectUri = "/";
    }
    redirectUri = "/";
    response.setHeader("Set-Cookie", "");
    response.setStatus(HttpStatus.FOUND.value());
    response.setHeader(HttpHeaders.LOCATION, redirectUri);
    response.sendRedirect(redirectUri);
    response.getWriter().flush();
    
    //MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    //MediaType jsonMimeType = MediaType.APPLICATION_JSON;
    //Map<String, String> message = Map.of("result", "logout success");
    //if (converter.canWrite(message.getClass(), jsonMimeType)) {
    //    converter.write(message, jsonMimeType, new ServletServerHttpResponse(response));
    //}
  }
}
