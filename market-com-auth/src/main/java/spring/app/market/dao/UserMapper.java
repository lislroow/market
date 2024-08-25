package spring.app.market.dao;

import org.apache.ibatis.annotations.Mapper;

import spring.app.market.common.user.SnsUser;
import spring.app.market.common.user.User;

@Mapper
public interface UserMapper {
  
  public int saveSnsUser(SnsUser param);
  public User selectUserBySnsUser(SnsUser param);
  public int insertUserWithSnsUser(SnsUser param);
  public int insertUserSnsRel(SnsUser param);
  
  public User selectUserByEmail(String email);
  
}
