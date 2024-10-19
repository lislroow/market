package market.api.product.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

public class ProductResDto implements Serializable {
  private static final long serialVersionUID = 1L;

  @Data
  public static class ItemRes implements Serializable {
    private static final long serialVersionUID = 1L;
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
  public static class ItemListRes implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<ProductResDto.ItemRes> list;
  }
  
  @Data
  public class ItemCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
  }
  
  @Data
  public class ItemQty implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer totalQty;
    private Integer soldQty;
  }
}
