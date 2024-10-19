package market.api.order.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;

public class OrderResDto implements Serializable {
  private static final long serialVersionUID = 1L;

  private OrderResDto() {}
  
  @Data
  public static class ItemRes implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    @JsonIgnoreProperties({"orders"})
    @ToString.Exclude
    private OrderResDto.ItemRes.Customer customerInfo;
    private String receiverName;
    private String receiverAddress;
    @JsonIgnoreProperties({"order"})
    @ToString.Exclude
    private List<OrderResDto.ItemRes.OrderItem> orderItemInfoList = new ArrayList<>();
    
    @Data
    public class Customer implements Serializable {
      private static final long serialVersionUID = 1L;
      private Integer id;
      private String name;
      @JsonIgnoreProperties({"customer"})
      @ToString.Exclude
      private List<OrderResDto.ItemRes> orders;
    }
    
    @Data
    public class OrderItem implements Serializable {
      private static final long serialVersionUID = 1L;
      private Integer orderQty;
      private OrderResDto.ItemRes.Product productInfo;
    }
    
    @Data
    public class Product implements Serializable {
      private static final long serialVersionUID = 1L;
      private Integer id;
    }
  }
  
  @Data
  public static class ItemListRes implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<OrderResDto.ItemRes> list;
  }
  
}
