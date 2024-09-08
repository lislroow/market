package market.api.customer.dto;

import lombok.Data;
import market.lib.enumcode.YN;

public class CustomerResDto {
  
  @Data
  public static class InfoRes {
    
    private String id;
    private String name;
    private String email;
    
  }
  
  @Data
  public static class DeliveryRes {
    private Integer id;
    private String address;
    private YN primaryYn;
  }
}
