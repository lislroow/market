package market.lib.vo;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Data;
import market.lib.enumcode.Role;

@Data
public class SessionUser implements OAuth2User, UserDetails {
  
  private static final long serialVersionUID = 2501815366855398147L;

  private String id;
  private String email;
  private String nickname;
  private String picture;
  private Role role;
  
  private User user;
  private Map<String,Object> attributes;
  
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
    Collection<? extends GrantedAuthority> authorities = 
        Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
    return authorities;
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
    boolean isAccountNonExpired = "N".equals(user.getDormantYn().name());
    return isAccountNonExpired;
  }
  @Override
  public boolean isAccountNonLocked() {
    boolean isAccountNonLocked = "N".equals(user.getLockedYn().name());
    return isAccountNonLocked;
  }
  @Override
  public boolean isCredentialsNonExpired() {
    boolean isCredentialsNonExpired = 
        user.getPasswordExpireDate().isAfter(LocalDateTime.now());
    return isCredentialsNonExpired;
  }
  @Override
  public boolean isEnabled() {
    boolean isEnabled = isAccountNonExpired()
        && isAccountNonLocked()
        && isCredentialsNonExpired();
    return isEnabled;
  }
  // --[UserDetails]
}
