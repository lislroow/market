package market.lib.enums;

//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;

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
  AL01("login required")
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
  
  //public static void search(String code) {
  //  List<RESPONSE_CODE> list = Arrays.stream(RESPONSE_CODE.values())
  //          .filter(item -> item.name().equals(code))
  //          .collect(Collectors.toList());
  //}
}
