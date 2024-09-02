package market.api.auth.sql;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import market.lib.vo.SnsUser;
import market.lib.vo.User;

@Mapper
public interface UserSql {
  
  public int saveSnsUser(SnsUser param);
  public User selectUserBySnsUser(SnsUser param);
  public int insertUserWithSnsUser(SnsUser param);
  public int insertUserSnsRel(SnsUser param);
  
  public User selectUserByEmail(@Param("email") String email);
  
}
