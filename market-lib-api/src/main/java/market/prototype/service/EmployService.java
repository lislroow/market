package market.prototype.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import market.common.util.Uuid;
import market.prototype.dao.EmployDao;
import market.prototype.vo.EmployVo;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class EmployService {
  
  private EmployDao employDao;
  
  @Transactional
  public EmployVo add(EmployVo.AddVo vo) {
    String id = Uuid.create();
    vo.setId(id);
    vo.setCreateId(id); // 로그인 사용자의 id 로 해야합니다.
    vo.setModifyId(id); // 로그인 사용자의 id 로 해야합니다.
    employDao.add(vo);
    
    EmployVo result = employDao.findById(vo.getId());
    
    // transaction 테스트를 위해 예외 발생
    // int i = 1 / 0;  // divide by 0 오류 발생으로 rollback 이 되어야 함
    return result;
  }
  
  @Transactional
  public void modifyNameById(EmployVo.ModifyVo vo) {
    // TODO
    vo.setModifyId(vo.getId());
    
    int cnt = employDao.modifyNameById(vo);
    if (cnt == 0) {
      throw new RuntimeException("수정 대상이 없습니다.");
    }
  }
  
  @Transactional
  public void removeById(EmployVo.RemoveVo vo) {
    int cnt = employDao.removeById(vo);
    if (cnt == 0) {
      throw new RuntimeException("삭제 대상이 없습니다.");
    }
  }
}
