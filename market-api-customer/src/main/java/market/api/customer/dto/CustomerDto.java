package market.api.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import market.lib.enumcode.YN;

public class CustomerDto {
  
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class InfoReq {
    private String id;
    private String name;
  }
  
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class InfoRes {
    
    private String id;
    private String name;
    private String email;
    
  }
  
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class DeliveryReq {
    private Integer id;
    private String address;
    private YN primaryYn;
  }
  
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class DeliveryRes {
    private Integer id;
    private String address;
    private YN primaryYn;
  }
}
