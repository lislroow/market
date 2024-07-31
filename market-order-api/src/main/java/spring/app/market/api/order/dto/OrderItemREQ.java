package spring.app.market.api.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemREQ {
  
  private Integer orderQty;
  
  private ProductREQ product;
  
}
