package market.api.order.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;

public class OrderResDto {
  
  @Data
  public static class ItemRes {
    private Integer id;
    @JsonIgnoreProperties({"orders"})
    @ToString.Exclude
    private OrderResDto.ItemRes.Customer customerInfo;
    private String receiverName;
    private String receiverAddress;
    @JsonIgnoreProperties({"order"})
    @ToString.Exclude
    private List<OrderResDto.ItemRes.OrderItem> orderItemInfoList = new ArrayList<OrderResDto.ItemRes.OrderItem>();
    
    @Data
    public class Customer {
      private Integer id;
      private String name;
      @JsonIgnoreProperties({"customer"})
      @ToString.Exclude
      private List<OrderResDto.ItemRes> orders;
    }
    
    @Data
    public class OrderItem {
      private Integer orderQty;
      private OrderResDto.ItemRes.Product productInfo;
    }
    
    @Data
    public class Product {
      private Integer id;
    }
  }
  
  @Data
  public static class ItemListRes {
    private List<OrderResDto.ItemRes> list;
  }
  
}
