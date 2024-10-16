package market.api.customer.dto;

import lombok.Data;
import market.lib.enumcode.YN;

public class MyInfoResDto {
  
  @Data
  public static class UserRes {
    
    private String id;
    private String name;
    private String email;
    
  }
  
  @Data
  public static class DeliveryAddressRes {
    private Integer id;
    private String address;
    private YN primaryYn;
  }
}
