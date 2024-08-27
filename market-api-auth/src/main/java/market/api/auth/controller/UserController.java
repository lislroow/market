package market.api.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import market.lib.config.webmvc.SessionContext;
import market.lib.vo.SessionUser;

@RestController
public class UserController {
  
  @GetMapping("/auth/v1/session")
  public SessionUser session() throws Exception {
    SessionUser user = SessionContext.getUser().get();
    return user;
  }
}
