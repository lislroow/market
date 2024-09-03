package market.api.customer.dto;

import lombok.Data;
import market.lib.enumcode.YN;

public class CustomerReqDto {

  @Data
  public static class InfoReq {
    private String id;
    private String name;
  }
  
  @Data
  public static class DeliveryReq {
    private Integer id;
    private String address;
    private YN primaryYn;
  }
}
