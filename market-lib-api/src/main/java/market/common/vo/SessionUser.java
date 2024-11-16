package market.common.vo;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Data;
import market.common.enumcode.Role;

@Data
public class SessionUser implements OAuth2User, UserDetails {
  
  private static final long serialVersionUID = 2501815366855398147L;

  private String id;
  private String email;
  private String nickname;
  private String picture;
  private Role role;
  
  private User user;
  private transient Map<String,Object> attributes;
  
  public SessionUser(User user) {
    this.user = user;
    
    this.id = user.getId();
    this.email = user.getEmail();
    this.nickname = user.getNickname();
    this.picture = user.getPicture();
    this.role = user.getRole();
  }
  
  public SessionUser(User user, Map<String, Object> attributes) {
    this.user = user;
    this.attributes = attributes;
    
    this.id = user.getId();
    this.email = user.getEmail();
    this.nickname = user.getNickname();
    this.picture = user.getPicture();
    this.role = user.getRole();
  }
  
  // [OAuth2User, UserDetails]
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
  }
  // --[OAuth2User, UserDetails]
  
  // [OAuth2User]
  @Override
  public Map<String, Object> getAttributes() {
    return this.attributes;
  }
  // --[OAuth2User]
  
  // [AuthenticatedPrincipal]
  @Override
  public String getName() {
    return this.nickname;
  }
  // --[AuthenticatedPrincipal]
  
  
  // [UserDetails]
  @Override
  public String getPassword() {
    return user.getPassword();
  }
  @Override
  public String getUsername() {
    return this.id;
  }
  @Override
  public boolean isAccountNonExpired() {
    return "N".equals(user.getDormantYn().name());
  }
  @Override
  public boolean isAccountNonLocked() {
    return "N".equals(user.getLockedYn().name());
  }
  @Override
  public boolean isCredentialsNonExpired() {
    return user.getPasswordExpireDate().isAfter(LocalDateTime.now());
  }
  @Override
  public boolean isEnabled() {
    return isAccountNonExpired()
        && isAccountNonLocked()
        && isCredentialsNonExpired();
  }
  // --[UserDetails]
}
