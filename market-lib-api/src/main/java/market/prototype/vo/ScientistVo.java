package market.prototype.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import market.common.audit.AuditVo;
import market.common.mybatis.Pageable;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class ScientistVo extends AuditVo {
  
  private String id;
  private String name;
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  @Builder
  public static class FindVo extends Pageable {
    private String id;
    private String name;
  }
  @Data
  @EqualsAndHashCode(callSuper=false)
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class AddVo extends AuditVo {
    private String id;
    private String name;
  }
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class ModifyVo extends AuditVo {
    private String id;
    private String name;
  }
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class RemoveVo extends AuditVo {
    private String id;
    private String name;
  }
}