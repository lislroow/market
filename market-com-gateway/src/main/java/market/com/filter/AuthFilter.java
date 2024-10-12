package market.com.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config>{
  
  public AuthFilter() {
    super(Config.class);
  }
  
  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      // pre process
      ServerHttpRequest request = exchange.getRequest();
      ServerHttpResponse response = exchange.getResponse();
      log.info("request id={}", request.getId());
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
