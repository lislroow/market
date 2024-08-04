package spring.app.market.api.product.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

  private Integer id;
  private String name;
  private String imgThumbUrl;
  
  private List<ProductCategoryDTO> categories;
  
  @JsonIgnoreProperties({"product"})
  private ProductInventoryDTO inventory;
  
}
