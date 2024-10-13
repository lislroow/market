package market.api.auth.controller.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import market.api.security.TokenService;
import market.lib.dto.ResponseDto;
import market.lib.dto.auth.TokenResDto;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FeignAuthController {

  private final TokenService tokenService;
  
  @GetMapping("/auth/v1/token/verify")
  public ResponseDto<TokenResDto.Verify> verifyToken(@RequestParam("tokenId") String tokenId) {
    log.info("tokenId={}", tokenId);

    TokenResDto.Verify dto = null;
    try {
      dto = tokenService.verifyToken(tokenId);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return ResponseDto.body(dto);
  }
  
}
