package spring.framework.common.user;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties("passwd")
public class SnsUser implements Serializable {
  
  private String snsId;
  private String site;
  private String email;
  private String nickname;
  private String passwd;
  private String picture;
  
  private String uid;
  
  @Builder
  public SnsUser(String snsId, String site, String email, String nickname, String picture, String uid) {
    this.snsId = snsId;
    this.site = site;
    this.email = email;
    this.nickname = nickname;
    this.picture = picture;
    this.uid = uid;
  }
}
