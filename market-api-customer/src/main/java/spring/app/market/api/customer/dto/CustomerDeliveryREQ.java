package spring.app.market.api.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.app.market.enumcode.YN;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDeliveryREQ {

  private Integer id;
  private String address;
  private YN primaryYn;
  
}
