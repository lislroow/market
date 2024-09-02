package market.api.order.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRES {

  private Integer id;
  private String name;
  private LocalDateTime createDate;
  private LocalDateTime modifyDate;
  
}
