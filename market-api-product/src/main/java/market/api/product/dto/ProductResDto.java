package market.api.product.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

public class ProductResDto {

  @Data
  public static class ItemRes {
    private Integer id;
    private String name;
    private String imgThumbUrl;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime createDate;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime modifyDate;
    
    private List<ItemCategory> categoryInfoList;
    
    private ItemQty qtyInfo;
  }
  
  @Data
  public static class ItemListRes {
    private List<ProductResDto.ItemRes> list;
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
