package market.api.customer.dto;

import java.io.Serializable;

import lombok.Data;
import market.common.enumcode.YN;

public class MyInfoResDto implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private MyInfoResDto() {}
  
  @Data
  public static class UserRes implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String email;
    
  }
  
  @Data
  public static class DeliveryAddressRes implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String address;
    private YN primaryYn;
  }
}
