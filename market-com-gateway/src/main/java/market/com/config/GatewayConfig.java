package market.com.config;

//import java.net.URLDecoder;
//import java.nio.charset.Charset;

import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


@Slf4j(topic = "ECS_JSON")
@Configuration
public class GatewayConfig {

  @Bean
  @Order(Ordered.LOWEST_PRECEDENCE)
  GlobalFilter customGlobalFilter() {
    return (exchange, chain) -> {
      //String url = URLDecoder.decode(exchange.getRequest().getURI().toString(), Charset.forName("utf-8"));
      MDC.put("requestUrl", exchange.getRequest().getURI().toString());
      MDC.put("clientIp", exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
      log.info("");
      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        //log.info("Global Filter: Post-processing, {}", exchange.getRequest().getURI());
      }));
    };
  }
}
