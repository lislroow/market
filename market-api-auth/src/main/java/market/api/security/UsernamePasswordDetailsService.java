package market.api.security;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import market.api.auth.repository.UserRepository;
import market.api.auth.sql.UserSql;
import market.lib.vo.SessionUser;
import market.lib.vo.User;

@Service
@RequiredArgsConstructor
public class UsernamePasswordDetailsService implements UserDetailsService {

  Logger ecslog = LoggerFactory.getLogger("ECS_JSON");
  
  final UserRepository userRepository;
  final UserSql userSql;
  final ModelMapper model;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    String email = username;
    MDC.put("userId", email);
    ecslog.info("");
    User user = userSql.selectUserByEmail(email);
    return new SessionUser(user);
  }
}
