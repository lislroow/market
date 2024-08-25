package spring.app.market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventoryREQ {
  
  private Integer id;
  private Integer totalQty;
  private Integer soldQty;
  
}
