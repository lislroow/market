package spring.app.market.dto;

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
  
  @JsonIgnoreProperties({"orderItems"})
  @ToString.Exclude
  private OrderRES order;
  
  private ProductRES product;
}