package market.api.product.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

public class ProductReqDto {

  @Data
  @JsonIgnoreProperties({"createDate", "modifyDate"})
  public static class ItemReq {
  
    private Integer id;
    private String name;
    private String imgThumbUrl;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createDate;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime modifyDate;
    
    private List<ItemCategory> categoryInfoList;
    
    private ItemQty qtyInfo;
  }
  
  @Data
  public static class ItemListReq {
    private List<ProductReqDto.ItemReq> list;
  }
  
  @Data
  public class ItemCategory {
    private String id;
    private String name;
  }
  
  @Data
  public class ItemQty {
    private Integer totalQty;
    private Integer soldQty;
  }
}
