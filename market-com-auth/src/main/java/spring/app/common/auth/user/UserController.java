package spring.app.common.auth.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.framework.common.user.SessionUser;
import spring.framework.config.webmvc.SessionContext;

@RestController
public class UserController {
  
  @GetMapping("/api/auth/user")
  public SessionUser user() throws Exception {
    SessionUser user = SessionContext.getUser().get();
    System.out.println(user);
    return user;
  }
}
