package market.api.customer.dto;

import lombok.Data;
import market.common.enumcode.YN;

public class MyInfoReqDto {
  
  private MyInfoReqDto() {}

  @Data
  public static class UserReq {
    private String id;
    private String name;
  }
  
  @Data
  public static class DeliveryAddressReq {
    private Integer id;
    private String address;
    private YN primaryYn;
  }
}
