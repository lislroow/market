package market.common.mybatis;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

public class DaoSupport {

  private SqlSessionTemplate sqlSessionTemplate;
  
  public DaoSupport(SqlSessionTemplate sqlSessionTemplate) {
    this.sqlSessionTemplate = sqlSessionTemplate;
  }
  
  public <T> T selectOne(String sqlid, Object param) {
    return sqlSessionTemplate.selectOne(sqlid, param);
  }
  public <E> List<E> selectList(String sqlid, Object param) {
    return sqlSessionTemplate.selectList(sqlid, param);
  }
  public <E> PagedList<E> selectPaging(String sqlid, Pageable param) {
    return (PagedList<E>) sqlSessionTemplate.selectList(sqlid, param);
  }
  public int insert(String sqlid, Object param) {
    return sqlSessionTemplate.insert(sqlid, param);
  }
  public int update(String sqlid, Object param) {
    return sqlSessionTemplate.update(sqlid, param);
  }
  public int delete(String sqlid, Object param) {
    return sqlSessionTemplate.delete(sqlid, param);
  }
}
