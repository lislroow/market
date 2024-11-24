package market.prototype.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import market.common.mybatis.Pageable;
import market.common.mybatis.PagedList;
import market.prototype.vo.ScientistVo;

@Mapper
public interface ScientistDao {
  
  List<ScientistVo> findAll();
  
  ScientistVo findById(@Param("id") String id);
  
  PagedList<ScientistVo> findList(Pageable param);
  
  PagedList<ScientistVo> findListByName(@Param("vo") ScientistVo.FindVo vo);
  
  int add(@Param("vo") ScientistVo.AddVo vo);
  
  int modifyNameById(@Param("vo") ScientistVo.ModifyVo vo);
  
  int removeById(@Param("vo") ScientistVo.RemoveVo vo);
  
}
