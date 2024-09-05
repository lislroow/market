package market.lib.dto.kafka;

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
public class OrderItemDto {
  
  private Integer id;
  private Integer orderQty;
  
  @JsonIgnoreProperties({"orderItems"})
  @ToString.Exclude
  private OrderDto order;
  
  private ProductDto product;
}
