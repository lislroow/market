package market.lib.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResponseDto<T> implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private ResponseDto(String code, String text) {
    this.head = new ResponseDto.Head();
    this.head.setCode(code);
    this.head.setText(text);
  }
  private ResponseDto(String code, String text, T data) {
    this(code, text);
    this.body = data;
  }
  private ResponseDto() {
    this("0000", "Success");
  }
  private ResponseDto(T data) {
    this();
    this.body = data;
  }
  
  @Data
  public static class Head {
    @ApiModelProperty(notes = "0000: success", position = 1)
    private String code;
    @ApiModelProperty(notes = "success", position = 2)
    private String text;
  }
  @ApiModelProperty(notes = "head", position = 1)
  private ResponseDto.Head head;
  
  @ApiModelProperty(notes = "body", position = 2)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private T body;
  
  public static <T> ResponseDto<T> body(String code, String text, T body) {
    return new ResponseDto<T>(code, text, body);
  }
  public static <T> ResponseDto<T> body(String code, String text) {
    return new ResponseDto<T>(code, text);
  }
  public static <T> ResponseDto<T> body(T body) {
    return new ResponseDto<T>(body);
  }
  public static <T> ResponseDto<T> body() {
    return new ResponseDto<T>();
  }
}
