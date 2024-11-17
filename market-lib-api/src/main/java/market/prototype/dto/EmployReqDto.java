package market.prototype.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import market.common.audit.AuditVo;

@Data
public class EmployReqDto {

  @Data
  @EqualsAndHashCode(callSuper=false)
  @Builder
  public static class AddDto extends AuditVo {
    private String id;
    private String name;
  }
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  @Builder
  public static class ModifyDto extends AuditVo {
    private String id;
    private String name;
  }
}
