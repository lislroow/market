package market.prototype.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import market.common.mybatis.Pageable;
import market.common.mybatis.PagedList;
import market.prototype.vo.EmployVo;

@Mapper
public interface EmployDao {
  
  List<EmployVo> findAll();
  
  EmployVo findById(@Param("id") String id);
  
  PagedList<EmployVo> findList(Pageable param);
  
  PagedList<EmployVo> findListByName(@Param("vo") EmployVo.FindVo vo);
  
  int add(@Param("vo") EmployVo.AddVo vo);
  
  int modifyNameById(@Param("vo") EmployVo.ModifyVo vo);
  
  int removeById(@Param("vo") EmployVo.RemoveVo vo);
  
}
