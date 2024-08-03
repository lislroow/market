package spring.app.common.auth.user;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import spring.app.common.auth.user.entity.UserEntity;

public interface UserRepository extends Repository<UserEntity, String> {

  UserEntity findByOauth2Id(String param);
  
  Optional<UserEntity> findByEmail(String param);
  
  UserEntity save(UserEntity param);
  
}
