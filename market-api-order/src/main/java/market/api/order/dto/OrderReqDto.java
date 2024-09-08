package market.api.order.dto;

import java.util.List;

import lombok.Data;

public class OrderReqDto {
  
  @Data
  public static class ItemReq {
    private String receiverName;
    private String receiverAddress;
    private List<OrderReqDto.ItemReq.OrderItem> orderItemInfoList;
    
    @Data
    public class OrderItem {
      private Integer orderQty;
      private OrderReqDto.ItemReq.OrderItem.Product productInfo;
      
      @Data
      public class Product {
        private Integer id;
      }
    }
  }

}
