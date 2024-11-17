package market.common.mybatis;

import lombok.Data;

@Data
public class Pageable {
  
  protected Integer page;
  protected Integer pageSize;
  
  public void setPageable(Pageable pageable) {
    this.page = pageable.page == null ? 1 : pageable.page;
    this.pageSize = pageable.pageSize == null ? 10 : pageable.pageSize;
  }
}