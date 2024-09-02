package market.api.security;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import market.api.auth.repository.UserRepository;
import market.api.auth.sql.UserSql;
import market.lib.vo.SessionUser;
import market.lib.vo.User;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  Logger ecslog = LoggerFactory.getLogger("ECS_JSON");
  
  @Autowired
  UserRepository userRepository;
  
  @Autowired
  UserSql userSql;
  
  @Autowired
  ModelMapper model;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    String email = username;
    MDC.put("userId", email);
    ecslog.info("");
    // jpa
    //UserEntity entity = userRepository.findByEmail(email).orElseThrow();
    //User user = model.map(entity, User.class);
    // mybatis
    User user = userSql.selectUserByEmail(email);
    SessionUser sessionUser = new SessionUser(user);
    return sessionUser;
  }
}
