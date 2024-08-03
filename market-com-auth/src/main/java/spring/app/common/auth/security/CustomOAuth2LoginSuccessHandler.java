package spring.app.common.auth.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomOAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    HttpSession session = request.getSession();
    //SessionUser sessionUser = (SessionUser) session.getAttribute("user");
    //ObjectMapper mapper = new ObjectMapper();
    //String json = mapper.writeValueAsString(sessionUser);
    //String b64 = new String(Base64.getEncoder().encode(json.getBytes()));
    //ResponseCookie resCookie = ResponseCookie.from("user", b64)
    //    .path("/")
    //    .build();
    ResponseCookie resCookie = ResponseCookie.from("LOGINED", "true")
        .path("/")
        .build();
        //.maxAge(0)
        //.httpOnly(true)
        //.secure(true)
    response.setHeader(HttpHeaders.SET_COOKIE, resCookie.toString());
    log.info("response-cookie: user="+resCookie.toString());
    session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    response.setHeader(HttpHeaders.LOCATION, "/");
    response.setStatus(HttpStatus.FOUND.value());
    response.getWriter().flush();
  }
}
