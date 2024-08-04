package market.com.gateway.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


@Slf4j(topic = "ESC_JSON")
@Configuration
public class GatewayConfig {

  @Bean
  @Order(Ordered.LOWEST_PRECEDENCE)
  GlobalFilter customGlobalFilter() {
    return (exchange, chain) -> {
      log.info("Global Filter: Pre-processing, {}", exchange.getRequest().getURI());
      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        log.info("Global Filter: Post-processing, {}", exchange.getRequest().getURI());
      }));
    };
  }
}
