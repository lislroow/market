package market.prototype.dto;

import java.util.List;

import lombok.Data;
import market.common.mybatis.Paged;
import market.prototype.vo.ScientistVo;

@Data
public class ScientistResDto {

  public static class Scientist extends ScientistVo { }
  
  @Data
  public static class ScientistList {
    private List<Scientist> list;
  }
  
  @Data
  public static class PagedScientistList {
    private Paged paged;
    private List<Scientist> list;
  }
  
}
