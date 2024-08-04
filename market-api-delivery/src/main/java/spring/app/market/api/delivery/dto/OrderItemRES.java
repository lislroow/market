package spring.app.market.api.delivery.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRES {
  
  private Integer id;
  private Integer orderQty;
  
  private ProductRES product;
}
