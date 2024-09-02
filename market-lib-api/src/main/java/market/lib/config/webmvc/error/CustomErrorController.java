package market.lib.config.webmvc.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class CustomErrorController 
  implements org.springframework.boot.web.servlet.error.ErrorController {
  
  @GetMapping("/error")
  public String error(@RequestParam String param) {
      return new String();
  }
  
}
