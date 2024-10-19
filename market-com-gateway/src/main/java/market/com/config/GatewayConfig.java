package market.com.config;

import java.nio.charset.StandardCharsets;

import org.slf4j.MDC;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import market.com.feign.AuthControllerFeign;
import market.com.filter.AuthFilter;
import market.lib.dto.ResponseDto;
import market.lib.enums.RESPONSE_CODE;
import reactor.core.publisher.Mono;


@Slf4j(topic = "ECS_JSON")
@Configuration
public class GatewayConfig {
  
  private static final ObjectMapper objectMapper = new ObjectMapper();
  
  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  ErrorWebExceptionHandler globalErrorHandler() {
    return (exchange, e) -> {
      MDC.put("requestUrl", exchange.getRequest().getURI().toString());
      MDC.put("clientIp", exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
      log.error("[{}] {}. {}", RESPONSE_CODE.G999.code(), RESPONSE_CODE.G999.message(), e.getCause() != null ? "/ cause: " + e.getCause().getClass() : e.getClass());
      
      exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
      exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
      
      ResponseDto<?> response = ResponseDto.body(RESPONSE_CODE.G999);
      DataBuffer buffer = null;
      try {
        buffer = exchange.getResponse()
            .bufferFactory()
            .wrap(objectMapper.writeValueAsBytes(response));
      } catch (JsonProcessingException e1) {
        RESPONSE_CODE responseCode = RESPONSE_CODE.G998;
        String errorMessage = "{\"header\": {\"code\": \""+responseCode.code()+"\", \"message\": \""+responseCode.message()+"\"}}";
        log.error("[{}] {}. {}", responseCode.code(), responseCode.message(), e.getCause() != null ? "/ cause: " + e.getCause().getClass() : e.getClass());
        buffer = exchange.getResponse()
            .bufferFactory()
            .wrap(errorMessage.getBytes(StandardCharsets.UTF_8));
      }
      exchange.getResponse().setStatusCode(HttpStatus.OK);
      exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
      return exchange.getResponse().writeWith(Mono.just(buffer));
    };
  }
  
  @Bean
  @Order(Ordered.LOWEST_PRECEDENCE)
  GlobalFilter globalFilter() {
    return (exchange, chain) -> 
      chain.filter(exchange).then(Mono.fromRunnable(() -> {
        MDC.put("requestUrl", exchange.getRequest().getURI().toString());
        MDC.put("clientIp", exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
        log.info("[{}] {}.", RESPONSE_CODE.S000.code(), RESPONSE_CODE.S000.message());
      }));
  }
  
  @Bean
  @Order(Ordered.LOWEST_PRECEDENCE - 1)
  AuthFilter authFilter(@Lazy AuthControllerFeign authControllerFeign) {
    log.debug("create authFilter");
    return new AuthFilter(authControllerFeign);
  }
}
