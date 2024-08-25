package spring.app.market.config.security;

import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<UserEntity, String> {

  UserEntity findByOauth2Id(String param);
  
  Optional<UserEntity> findByEmail(String param);
  
  UserEntity save(UserEntity param);
  
}
