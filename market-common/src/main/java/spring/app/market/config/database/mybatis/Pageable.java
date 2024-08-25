package spring.app.market.config.database.mybatis;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Pageable <T extends Object> {
  
  protected Integer page;
  protected Integer pageSize;
}