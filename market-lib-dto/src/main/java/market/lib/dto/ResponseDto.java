package market.lib.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ResponseDto<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  private ResponseDto() {
    this.header = new Header();
    this.header.code = "0000";
    this.header.message = "Success";
  }
  
  private ResponseDto(T data) {
    this.header = new Header();
    this.header.code = "0000";
    this.header.message = "Success";
    this.body = data;
  }
  
  private ResponseDto(String code, String message, T data) {
    this.header = new Header();
    this.header.code = code;
    this.header.message = message;
    this.body = data;
  }
  
  private ResponseDto(String code, String message) {
    this.header = new Header();
    this.header.setCode(code);
    this.header.setMessage(message);
  }
  
  private Header header = new Header();
  private T body;
  
  @Data
  public static class Header {
    private String code;
    private String message;
  }
  
  public static<T> ResponseDto<T> body(String code, String message, T data) {
    return new ResponseDto<T>(code, message, data);
  }
  
  public static<T> ResponseDto<T> body(String code, String message) {
    return new ResponseDto<T>(code, message);
  }
  
  public static<T> ResponseDto<T> body(T data) {
    return new ResponseDto<T>(data);
  }

  public static<T> ResponseDto<T> body() {
    return new ResponseDto<T>();
  }

}
