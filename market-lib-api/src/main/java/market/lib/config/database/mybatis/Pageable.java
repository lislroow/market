package market.lib.config.database.mybatis;

import lombok.Data;

@Data
public class Pageable {
  
  protected Integer page;
  protected Integer pageSize;
}