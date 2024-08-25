package market.lib.dto;

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
public class OrderDTO {

  private Integer id;
  
  @JsonIgnoreProperties({"orders"})
  @ToString.Exclude
  private CustomerDTO customer;
  
  private String receiverName;
  private String receiverAddress;
  
  @JsonIgnoreProperties({"order"})
  @ToString.Exclude
  private List<OrderItemDTO> orderItems = new ArrayList<OrderItemDTO>();

}
