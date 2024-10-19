package market.com.filter;

import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import market.com.feign.AuthControllerFeign;
import market.lib.constant.Constant;
import market.lib.dto.ResponseDto;
import market.lib.dto.auth.TokenResDto;
import market.lib.enums.RESPONSE_CODE;
import market.lib.exception.MarketException;
import reactor.core.publisher.Mono;

@Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
  
  private AuthControllerFeign authControllerFeign;
  
  public AuthFilter(AuthControllerFeign authControllerFeign) {
    super(Config.class);
    this.authControllerFeign = authControllerFeign;
  }
  
  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      // pre process
      ServerHttpRequest request = exchange.getRequest();
      ServerHttpResponse response = exchange.getResponse();
      List<String> headerTokens = request.getHeaders().get(Constant.X_TOKEN_ID);
      String tokenId = null;
      if (headerTokens == null || headerTokens.isEmpty()) {
        HttpCookie cookieToken = request.getCookies().getFirst(Constant.X_TOKEN_ID);
        tokenId = cookieToken != null ? cookieToken.getValue() : null;
      } else if (headerTokens != null && !headerTokens.isEmpty()) {
        tokenId = headerTokens.stream().findFirst().get();
      }
      
      if (StringUtils.hasText(tokenId)) {
        log.info(Constant.X_TOKEN_ID+"={}", tokenId);
        log.debug("authFeign={}", authControllerFeign);
        ResponseDto<TokenResDto.Verify> resDto = authControllerFeign.verifyToken(tokenId);
        if (resDto.getBody() == null) {
          throw new MarketException(RESPONSE_CODE.A002);
        }
        
        String userId = resDto.getBody().getUserId();
        ServerHttpRequest modifiedRequest = request.mutate()
            .header(Constant.X_TOKEN_ID, userId)
            .build();
        ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
        return chain.filter(modifiedExchange).then(Mono.fromRunnable(() -> 
          // post process
          log.debug("response status={}", response.getStatusCode())
        ));
      }
      return chain.filter(exchange).then(Mono.fromRunnable(() -> 
        // post process
        log.debug("response status={}", response.getStatusCode())
      ));
    };
  }
  
  public static class Config {
    long id = -1;
  }
}
