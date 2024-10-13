package market.lib.dto.auth;

import lombok.Data;

@Data
public class TokenResDto {
  
  @Data
  public static class Verify {
    private String userId;
  }
}
