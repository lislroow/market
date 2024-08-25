package market.lib.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import market.lib.enumcode.Role;
import market.lib.enumcode.YN;

@Data
public class User implements Serializable {
  
  private static final long serialVersionUID = -8630073760280805171L;
  
  private String id;
  private String password;
  private String email;
  private String nickname;
  private String picture;
  private Role role;
  private YN lockedYn;
  private YN dormantYn;
  private LocalDateTime passwordExpireDate;
  private String registrationId;
  private String oauth2Id;
  private LocalDateTime createDate;
  private LocalDateTime modifyDate;
  
}
