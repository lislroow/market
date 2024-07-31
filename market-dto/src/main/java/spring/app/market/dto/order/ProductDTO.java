package spring.app.market.dto.order;

import java.time.LocalDateTime;

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
  private LocalDateTime createDate;
  private LocalDateTime modifyDate;
  
}
