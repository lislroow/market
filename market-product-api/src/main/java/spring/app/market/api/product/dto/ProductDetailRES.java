package spring.app.market.api.product.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailRES {

  private Integer id;
  private String name;
  private String imgThumbUrl;
  
  private List<ProductCategoryDTO> categories;
  
  private ProductInventoryDTO inventory;
  
}
