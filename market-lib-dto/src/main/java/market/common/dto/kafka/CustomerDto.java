package market.common.dto.kafka;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
  
  private String id;
  private String name;
  
  @JsonIgnoreProperties({"customer"})
  @ToString.Exclude
  private List<OrderDto> orders;
}
