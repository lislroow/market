package spring.app.common.auth.security;

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
import spring.app.common.auth.user.UserRepository;
import spring.app.common.auth.user.entity.UserEntity;
import spring.framework.common.user.SessionUser;
import spring.framework.common.user.User;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  Logger ecslog = LoggerFactory.getLogger("ECS_JSON");
  
  @Autowired
  UserRepository userRepository;
  
  @Autowired
  ModelMapper model;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    String email = username;
    MDC.put("userId", email);
    ecslog.info("");
    UserEntity entity = userRepository.findByEmail(email).orElseThrow();
    User user = model.map(entity, User.class);
    SessionUser sessionUser = new SessionUser(user);
    return sessionUser;
  }
}
