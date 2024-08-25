package spring.app.market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.app.market.enumcode.YN;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDeliveryRES {

  private Integer id;
  private String address;
  private YN primaryYn;
  
}
