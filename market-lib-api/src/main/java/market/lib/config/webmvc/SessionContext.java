package market.lib.config.webmvc;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;

import market.lib.vo.SessionUser;

public class SessionContext {
  
  public static Optional<SessionUser> getUser() {
    SessionUser user = null;
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof SessionUser) {
      user = (SessionUser) principal;
    } else if (principal instanceof String) {
      String userId = (String) principal;
    }
    return Optional.ofNullable(user);
  }
  
}
