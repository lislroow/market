package market.common.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import market.common.constant.Constant;
import market.common.dto.ResponseDto;
import market.common.dto.TokenResDto;
import market.common.enumcode.RESPONSE_CODE;
import market.common.exception.MarketException;
import market.common.redis.RedisSupport;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
  
  public AuthFilter() {
    super(AuthFilter.Config.class);
  }
  
  @Autowired
  @Qualifier(Constant.REDIS.AUTH_USER + "RedisSupport")
  private RedisSupport authUserRedisSupport;
  
  @Autowired
  @Qualifier(Constant.REDIS.AUTH_GUEST + "RedisSupport")
  private RedisSupport authGuestRedisSupport;
  
  
  @Override
  public GatewayFilter apply(AuthFilter.Config config) {
    return (exchange, chain) -> {
      // pre process
      ServerHttpRequest request = exchange.getRequest();
      ServerHttpResponse response = exchange.getResponse();
      List<String> headerTokens = request.getHeaders().get(Constant.HTTP_HEADER.X_TOKEN_ID);
      
      // 1) tokenId 확보
      String tokenId = null;
      {
        if (headerTokens == null || headerTokens.isEmpty()) {
          HttpCookie cookieToken = request.getCookies().getFirst(Constant.HTTP_HEADER.X_TOKEN_ID);
          tokenId = cookieToken != null ? cookieToken.getValue() : null;
        } else if (headerTokens != null && !headerTokens.isEmpty()) {
          tokenId = headerTokens.stream().findFirst().get();
        }
      }
      
      // 2) token 검증
      {
        ObjectMapper objectMapper = new ObjectMapper();
        
        String accessToken = null;
      }
      if (StringUtils.hasText(tokenId)) {
        log.info(Constant.HTTP_HEADER.X_TOKEN_ID+"={}", tokenId);
        //ResponseDto<TokenResDto.Verify> resDto = authControllerFeign.verifyToken(tokenId);
        ResponseDto<TokenResDto.Verify> resDto = null;
        if (resDto.getBody() == null) {
          throw new MarketException(RESPONSE_CODE.A002);
        }
        
        String userId = resDto.getBody().getUserId();
        ServerHttpRequest modifiedRequest = request.mutate()
            .header(Constant.HTTP_HEADER.X_TOKEN_ID, userId)
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
  
  @Data
  public static class Config {
    private Long id = -1L;
  }
}
