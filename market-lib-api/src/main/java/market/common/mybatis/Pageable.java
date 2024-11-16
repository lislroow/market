package market.common.mybatis;

import lombok.Data;

@Data
public class Pageable {
  
  protected Integer page;
  protected Integer pageSize;
}