package market.common.dto.kafka;

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
public class OrderDto {

  private Integer id;
  
  @JsonIgnoreProperties({"orders"})
  @ToString.Exclude
  private CustomerDto customer;
  
  private String receiverName;
  private String receiverAddress;
  
  @JsonIgnoreProperties({"order"})
  @ToString.Exclude
  //private List<OrderItemDto> orderItems = new ArrayList<OrderItemDto>(); // '@Builder' 가 있을 경우 무시됨
  private List<OrderItemDto> orderItems;

}
