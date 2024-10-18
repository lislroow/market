package market.lib.enums;

public enum RESPONSE_CODE {
  
  /** 성공 */
  S000("Success"),
  E999("unknown error"),
  G001("api gateway error"),
  G002("server status 503"),
  G998("ResponseDto creation error"),
  G999("unknown gateway error"),
  A001("token creation error"),
  A002("token verification error"),
  A003("token is empty"),
  AL01("login required"),
  C001("illegal access"),
  C002("fail to save")
  ;
  
  private String message;

  RESPONSE_CODE(String message){
    this.message = message;
  }
  
  public String message() {
    return this.message;
  }
  
  public String code() {
    return this.name();
  }
}
