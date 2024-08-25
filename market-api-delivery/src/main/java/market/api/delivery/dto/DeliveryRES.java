package market.api.delivery.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import market.api.delivery.entity.Delivery;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRES {
  
  private Integer id;
  private Integer orderId;
  private Integer orderItemId;
  
  private String receiverName;
  private String receiverAddress;
  
  @JsonIgnoreProperties({"delivery"})
  @ToString.Exclude
  private OrderItemRES orderItem;
  
  public static DeliveryRES create(Delivery entity) {
    DeliveryRES res = new DeliveryRES();
    res.id = entity.getId().getId();
    res.orderId = entity.getId().getOrderId();
    res.orderItemId = entity.getId().getOrderItemId();
    res.receiverName = entity.getReceiverName();
    res.receiverAddress = entity.getReceiverAddress();
    return res;
  }
}
