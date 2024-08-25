package spring.app.market.api.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.app.market.common.user.SessionUser;
import spring.app.market.config.webmvc.SessionContext;

@RestController
public class UserController {
  
  @GetMapping("/api/auth/user")
  public SessionUser user() throws Exception {
    SessionUser user = SessionContext.getUser().get();
    System.out.println(user);
    return user;
  }
}
