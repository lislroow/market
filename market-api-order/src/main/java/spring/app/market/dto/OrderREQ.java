package spring.app.market.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderREQ {

  private String receiverName;
  private String receiverAddress;
  
  private List<OrderItemREQ> orderItems;
}
