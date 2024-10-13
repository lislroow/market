package market.com.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import market.com.feign.AuthFeign;
import market.lib.constant.Constant;
import market.lib.dto.ResponseDto;
import market.lib.dto.auth.TokenResDto;
import reactor.core.publisher.Mono;

@Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
  
  private AuthFeign authFeign;
  
  public AuthFilter(AuthFeign authFeign) {
    super(Config.class);
    this.authFeign = authFeign;
  }
  
  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      // pre process
      ServerHttpRequest request = exchange.getRequest();
      ServerHttpResponse response = exchange.getResponse();
      if (request.getCookies().get(Constant.X_TOKEN_ID) != null) {
        String tokenId = request.getCookies().get(Constant.X_TOKEN_ID).get(0).getValue();
        log.info(Constant.X_TOKEN_ID+"={}", tokenId);
        log.debug("authFeign={}", authFeign);
        ResponseDto<TokenResDto.Verify> resDto = authFeign.verifyToken(tokenId);
        String userId = resDto.getBody().getUserId();
        ServerHttpRequest modifiedRequest = request.mutate()
            .header("X-USER_ID", userId)
            .build();
        ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
        return chain.filter(modifiedExchange).then(Mono.fromRunnable(() -> {
          // post process
          log.info("response status={}", response.getStatusCode());
        }));
      } else {
        log.info("tokenId is null");
      }
      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        // post process
        log.info("response status={}", response.getStatusCode());
      }));
    };
  }
  
  public static class Config {
    // put the configuration properties
  }
}
