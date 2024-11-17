package market.common.audit;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AuditVo {

  private LocalDateTime createDate;
  private LocalDateTime modifyDate;
  private String createId;
  private String modifyId;
  
}
