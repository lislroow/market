package spring.app.market.dto.order;

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
public class OrderItemDTO {
  
  private Integer id;
  private Integer orderQty;
  
  @JsonIgnoreProperties({"orderItems"})
  @ToString.Exclude
  private OrderDTO order;
  
  private ProductDTO product;
}
