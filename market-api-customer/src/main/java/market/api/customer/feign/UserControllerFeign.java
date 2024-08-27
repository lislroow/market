package market.api.customer.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "market-api-auth")
public interface UserControllerFeign {
  
  @GetMapping("/auth/v1/test")
  public List<String> test();
  
}
