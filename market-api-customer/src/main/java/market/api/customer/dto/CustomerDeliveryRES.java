package market.api.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import market.lib.enumcode.YN;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDeliveryRES {

  private Integer id;
  private String address;
  private YN primaryYn;
  
}
