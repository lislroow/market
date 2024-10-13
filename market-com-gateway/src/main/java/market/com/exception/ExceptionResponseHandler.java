package market.com.exception;

import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import market.lib.dto.ResponseDto;
import market.lib.enums.RESPONSE_CODE;
import market.lib.exception.MarketException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
@AllArgsConstructor
public class ExceptionResponseHandler {
  
  @ExceptionHandler({MarketException.class})
  @ResponseStatus(HttpStatus.OK)
  protected Mono<ResponseDto<?>> handleMarketException(MarketException e, ServerWebExchange exchange) {
    log.error("[{}] {}. {}", e.getErrorCode(), e.getErrorMessage(), e.getCause() != null ? "/ cause: " + e.getCause().getClass() : "");
    ResponseDto<?> response = ResponseDto.body(e.getErrorCode(), e.getMessage());
    return Mono.just(response);
  }
  
  @ExceptionHandler({FeignException.class})
  @ResponseStatus(HttpStatus.OK)
  protected Mono<ResponseDto<?>> handleFeignException(FeignException e, ServerWebExchange exchange) {
    RESPONSE_CODE responseCode = RESPONSE_CODE.G001;
    log.error("[{}] {}. {}", responseCode.code(), responseCode.message(), e.getCause() != null ? "/ cause: " + e.getCause().getClass() : e.getClass());
    ResponseDto<?> response = ResponseDto.body(responseCode);
    return Mono.just(response);
  }
  
  @ExceptionHandler({NotFoundException.class})
  @ResponseStatus(HttpStatus.OK)
  protected Mono<ResponseDto<?>> handleNotFoundException(NotFoundException e, ServerWebExchange exchange) {
    RESPONSE_CODE responseCode = RESPONSE_CODE.G002;
    log.error("[{}] {}. {}", responseCode.code(), responseCode.message(), e.getCause() != null ? "/ cause: " + e.getCause().getClass() : e.getClass());
    ResponseDto<?> response = ResponseDto.body(responseCode);
    return Mono.just(response);
  }
}
