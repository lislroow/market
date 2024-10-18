package market.com.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import market.com.config.FeignConfig;
import market.lib.dto.ResponseDto;
import market.lib.dto.auth.TokenResDto;

@FeignClient(name = "market-api-auth", url = "http://localhost:20000", configuration = { FeignConfig.class })
public interface AuthControllerFeign {

  @GetMapping("/auth/v1/token/verify")
  ResponseDto<TokenResDto.Verify> verifyToken(@RequestParam("tokenId") String tokenId);
  
}
