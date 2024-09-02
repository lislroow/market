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

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    ResponseCookie resCookie = ResponseCookie.from("LOGINED", "true")
        .path("/")
        .build();
    response.setHeader(HttpHeaders.SET_COOKIE, resCookie.toString());
    log.info("response-cookie: user="+resCookie.toString());
    
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    MediaType jsonMimeType = MediaType.APPLICATION_JSON;
    Map<String, String> message = Map.of("result", "AuthenticationSuccess");
    if (converter.canWrite(message.getClass(), jsonMimeType)) {
        converter.write(message, jsonMimeType, new ServletServerHttpResponse(response));
    }
  }
}
