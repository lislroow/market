package market.api.auth.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import market.common.vo.SnsUser;
import market.common.vo.User;

@Mapper
public interface UserDao {
  
  public int saveSnsUser(SnsUser param);
  public User selectUserBySnsUser(SnsUser param);
  public int insertUserWithSnsUser(SnsUser param);
  public int insertUserSnsRel(SnsUser param);
  
  public User selectUserByEmail(@Param("email") String email);
  
}
