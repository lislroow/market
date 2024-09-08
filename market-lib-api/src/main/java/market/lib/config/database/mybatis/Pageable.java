package market.lib.config.database.mybatis;

import lombok.Data;

@Data
public class Pageable <T extends Object> {
  
  protected Integer page;
  protected Integer pageSize;
}