package market.prototype.dto;

import java.util.List;

import lombok.Data;
import market.common.mybatis.Paged;
import market.prototype.vo.EmployVo;

@Data
public class EmployResDto {

  public static class Employ extends EmployVo { }
  
  @Data
  public static class EmployList {
    private List<Employ> list;
  }
  
  @Data
  public static class PagedEmployList {
    private Paged paged;
    private List<Employ> list;
  }
  
}
