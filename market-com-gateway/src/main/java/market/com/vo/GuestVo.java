package market.com.vo;

import java.time.LocalDateTime;

import lombok.Data;

public class GuestVo {

  @Data
  public static class GuestToken implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    
    private String uuid;
    private String token;
    private LocalDateTime expireTime;
  }
  
  @Data
  public static class Backoff implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    
    private String clientIp;
    private Integer failCount;
    private LocalDateTime delayTime;
  }
}
