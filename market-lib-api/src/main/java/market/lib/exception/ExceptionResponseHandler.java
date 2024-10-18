package market.lib.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import market.lib.dto.ResponseDto;
import market.lib.enums.RESPONSE_CODE;

@ControllerAdvice
@RestController
@Slf4j
@AllArgsConstructor
public class ExceptionResponseHandler extends ResponseEntityExceptionHandler {
  
  static final String LOGFMT = "[{}] {}. {}";
  static final String CAUSE = "/ cause: ";
  
  @ExceptionHandler({MarketException.class})
  @ResponseStatus(HttpStatus.OK)
  protected ResponseDto<Object> handleMarketException(MarketException e, WebRequest request) {
    log.error(LOGFMT, e.getErrorCode(), e.getErrorMessage(), e.getCause() != null ? CAUSE + e.getCause().getClass() : e.getClass());
    return ResponseDto.body(e.getErrorCode(), e.getErrorMessage());
  }
  
  @ExceptionHandler({FeignException.class})
  @ResponseStatus(HttpStatus.OK)
  protected ResponseDto<Object> handleFeignException(FeignException e) {
    RESPONSE_CODE responseCode = RESPONSE_CODE.G001;
    log.error(LOGFMT, responseCode.code(), responseCode.message(), e.getCause() != null ? CAUSE + e.getCause().getClass() : e.getClass());
    return ResponseDto.body(responseCode.code(), responseCode.message());
  }
  
  @ExceptionHandler({Exception.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected ResponseDto<Object> handleException(Exception e) {
    RESPONSE_CODE responseCode = RESPONSE_CODE.E999;
    log.error(LOGFMT, responseCode.code(), responseCode.message(), e.getCause() != null ? CAUSE + e.getCause().getClass() : e.getClass());
    return ResponseDto.body(responseCode.code(), responseCode.message());
  }
}
