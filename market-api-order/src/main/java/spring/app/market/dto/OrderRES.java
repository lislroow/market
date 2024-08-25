package spring.app.market.dto;

import java.util.ArrayList;
import java.util.List;

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
public class OrderRES {

  private Integer id;
  
  @JsonIgnoreProperties({"orders"})
  @ToString.Exclude
  private CustomerRES customer;
  
  private String receiverName;
  private String receiverAddress;
  
  @JsonIgnoreProperties({"order"})
  @ToString.Exclude
  private List<OrderItemRES> orderItems = new ArrayList<OrderItemRES>();
}
