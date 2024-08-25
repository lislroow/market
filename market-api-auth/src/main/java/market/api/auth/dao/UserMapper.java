package market.api.auth.dao;

import org.apache.ibatis.annotations.Mapper;

import market.lib.vo.SnsUser;
import market.lib.vo.User;

@Mapper
public interface UserMapper {
  
  public int saveSnsUser(SnsUser param);
  public User selectUserBySnsUser(SnsUser param);
  public int insertUserWithSnsUser(SnsUser param);
  public int insertUserSnsRel(SnsUser param);
  
  public User selectUserByEmail(String email);
  
}
