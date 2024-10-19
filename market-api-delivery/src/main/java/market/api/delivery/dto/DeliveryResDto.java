package market.api.delivery.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;
import market.api.delivery.entity.Delivery;

public class DeliveryResDto {
  
  private DeliveryResDto() {}
  
  @Data
  public static class StatusRes {
    private Integer id;
    private Integer orderId;
    private Integer orderItemId;
    
    private String receiverName;
    private String receiverAddress;
    
    @JsonIgnoreProperties({"delivery"})
    @ToString.Exclude
    private DeliveryResDto.OrderItemRes orderItem;
    
    public static DeliveryResDto.StatusRes create(Delivery entity) {
      DeliveryResDto.StatusRes res = new DeliveryResDto.StatusRes();
      res.id = entity.getId().getId();
      res.orderId = entity.getId().getOrderId();
      res.orderItemId = entity.getId().getOrderItemId();
      res.receiverName = entity.getReceiverName();
      res.receiverAddress = entity.getReceiverAddress();
      return res;
    }
  }
  
  @Data
  public static class OrderItemRes {
    private Integer id;
    private Integer orderQty;
    private DeliveryResDto.ProductRes product;
  }
  
  @Data
  public static class ProductRes {
    private Integer id;
    private String name;
    private String imgThumbUrl;
  }
}
