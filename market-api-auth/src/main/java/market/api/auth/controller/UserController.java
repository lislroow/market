package market.api.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import market.lib.config.webmvc.SessionContext;
import market.lib.vo.SessionUser;

@RestController
public class UserController {
  
  @GetMapping("/api/auth/user")
  public SessionUser user() throws Exception {
    SessionUser user = SessionContext.getUser().get();
    System.out.println(user);
    return user;
  }
}
