package market.prototype.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import market.common.dto.ResponseDto;
import market.prototype.dao.EmployDao;
import market.prototype.dto.EmployResDto;
import market.prototype.vo.EmployVo;

@RestController
@RequiredArgsConstructor
public class MybatisController {

  final ModelMapper modelMapper;
  
  @Autowired(required = false)
  @Qualifier(value = "employDao")
  private EmployDao employDao;
  
  @Autowired(required = false)
  private EmployDao employPrimaryDao;

  @Autowired(required = false)
  @Qualifier(value = "employH2Dao")
  private EmployDao employH2Dao;
  
  @Autowired(required = false)
  @Qualifier(value = "employMariaDao")
  private EmployDao employMariaDao;
  
  @Autowired(required = false)
  @Qualifier(value = "employOracleDao")
  private EmployDao employOracleDao;
  
  @Autowired(required = false)
  @Qualifier(value = "employPostgresDao")
  private EmployDao employPostgresDao;
  
  @GetMapping("/prototype/v1/all")
  public ResponseDto<EmployResDto.EmployList> all() {
    EmployResDto.EmployList resDto = new EmployResDto.EmployList();
    List<EmployResDto.Employ> listAll = new ArrayList<EmployResDto.Employ>();
    listAll.addAll(employH2Dao.findAll().stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList()));
    listAll.addAll(employMariaDao.findAll().stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList()));
    listAll.addAll(employOracleDao.findAll().stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList()));
    listAll.addAll(employPostgresDao.findAll().stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList()));
    resDto.setList(listAll);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/prototype/v1/standard")
  public ResponseDto<EmployResDto.EmployList> standard() {
    List<EmployVo> result = employDao.findAll();
    List<EmployResDto.Employ> list = result.stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList());
    EmployResDto.EmployList resDto = new EmployResDto.EmployList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/prototype/v1/primary")
  public ResponseDto<EmployResDto.EmployList> primary() {
    List<EmployVo> result = employPrimaryDao.findAll();
    List<EmployResDto.Employ> list = result.stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList());
    EmployResDto.EmployList resDto = new EmployResDto.EmployList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/prototype/v1/h2")
  public ResponseDto<EmployResDto.EmployList> h2() {
    List<EmployVo> result = employH2Dao.findAll();
    List<EmployResDto.Employ> list = result.stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList());
    EmployResDto.EmployList resDto = new EmployResDto.EmployList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/prototype/v1/maria")
  public ResponseDto<EmployResDto.EmployList> maria() {
    List<EmployVo> result = employMariaDao.findAll();
    List<EmployResDto.Employ> list = result.stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList());
    EmployResDto.EmployList resDto = new EmployResDto.EmployList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/prototype/v1/oracle")
  public ResponseDto<EmployResDto.EmployList> oracle() {
    List<EmployVo> result = employOracleDao.findAll();
    List<EmployResDto.Employ> list = result.stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList());
    EmployResDto.EmployList resDto = new EmployResDto.EmployList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/prototype/v1/postgres")
  public ResponseDto<EmployResDto.EmployList> postgres() {
    List<EmployVo> result = employPostgresDao.findAll();
    List<EmployResDto.Employ> list = result.stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList());
    EmployResDto.EmployList resDto = new EmployResDto.EmployList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
}
