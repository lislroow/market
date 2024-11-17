package market.common.exception;

import market.common.enumcode.RESPONSE_CODE;

public class MarketException extends RuntimeException {
  
  private static final long serialVersionUID = 1L;
  
  private final String errorCode;
  private final String errorMessage;

  public MarketException(String errorCode, String errorMessage) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public MarketException(String errorMessage, Throwable cause) {
    super(errorMessage, cause);
    this.errorMessage = errorMessage;
    this.errorCode = "";
  }

  public MarketException(String errorCode) {
    super(errorCode);
    this.errorCode = errorCode;
    this.errorMessage = "";
  }

  public MarketException(RESPONSE_CODE responseCode) {
    super(responseCode.code());
    this.errorCode = responseCode.code();
    this.errorMessage = responseCode.message();
  }

  public MarketException(RESPONSE_CODE responseCode, Throwable cause) {
    super(responseCode.code(), cause);
    this.errorCode = responseCode.code();
    this.errorMessage = responseCode.message();
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}