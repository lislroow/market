package market.common.exception;

import java.io.Serializable;

import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import market.common.dto.ResponseDto;
import market.common.enumcode.RESPONSE_CODE;
import market.common.exception.MarketException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
@AllArgsConstructor
public class ExceptionResponseHandler {
  
  static final String LOGFMT = "[{}] {}. {}";
  static final String CAUSE = "/ cause: ";
  
  @ExceptionHandler({MarketException.class})
  @ResponseStatus(HttpStatus.OK)
  protected Mono<ResponseDto<Serializable>> handleMarketException(MarketException e, ServerWebExchange exchange) {
    log.error(LOGFMT, e.getErrorCode(), e.getErrorMessage(), e.getCause() != null ? CAUSE + e.getCause().getClass() : "");
    ResponseDto<Serializable> response = ResponseDto.body(e.getErrorCode(), e.getMessage());
    return Mono.just(response);
  }
  
  @ExceptionHandler({FeignException.class})
  @ResponseStatus(HttpStatus.OK)
  protected Mono<ResponseDto<Serializable>> handleFeignException(FeignException e, ServerWebExchange exchange) {
    RESPONSE_CODE responseCode = RESPONSE_CODE.G001;
    log.error(LOGFMT, responseCode.code(), responseCode.message(), e.getCause() != null ? CAUSE + e.getCause().getClass() : e.getClass());
    ResponseDto<Serializable> response = ResponseDto.body(responseCode);
    return Mono.just(response);
  }
  
  @ExceptionHandler({NotFoundException.class})
  @ResponseStatus(HttpStatus.OK)
  protected Mono<ResponseDto<Serializable>> handleNotFoundException(NotFoundException e, ServerWebExchange exchange) {
    RESPONSE_CODE responseCode = RESPONSE_CODE.G002;
    log.error(LOGFMT, responseCode.code(), responseCode.message(), e.getCause() != null ? CAUSE + e.getCause().getClass() : e.getClass());
    ResponseDto<Serializable> response = ResponseDto.body(responseCode);
    return Mono.just(response);
  }
}
