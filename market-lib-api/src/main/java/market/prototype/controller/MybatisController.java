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
import market.prototype.dao.ScientistDao;
import market.prototype.dto.ScientistResDto;
import market.prototype.vo.ScientistVo;

@RestController
@RequiredArgsConstructor
public class MybatisController {

  final ModelMapper modelMapper;
  
  @Autowired(required = false)
  @Qualifier(value = "scientistDao")
  private ScientistDao scientistDao;
  
  @Autowired(required = false)
  private ScientistDao scientistPrimaryDao;

  @Autowired(required = false)
  @Qualifier(value = "scientistH2Dao")
  private ScientistDao scientistH2Dao;
  
  @Autowired(required = false)
  @Qualifier(value = "scientistMariaDao")
  private ScientistDao scientistMariaDao;
  
  @Autowired(required = false)
  @Qualifier(value = "scientistOracleDao")
  private ScientistDao scientistOracleDao;
  
  @Autowired(required = false)
  @Qualifier(value = "scientistPostgresDao")
  private ScientistDao scientistPostgresDao;
  
  @GetMapping("/mybatis/v1/all")
  public ResponseDto<ScientistResDto.ScientistList> all() {
    ScientistResDto.ScientistList resDto = new ScientistResDto.ScientistList();
    List<ScientistResDto.Scientist> listAll = new ArrayList<ScientistResDto.Scientist>();
    listAll.addAll(scientistH2Dao.findAll().stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList()));
    listAll.addAll(scientistMariaDao.findAll().stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList()));
    listAll.addAll(scientistOracleDao.findAll().stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList()));
    listAll.addAll(scientistPostgresDao.findAll().stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList()));
    resDto.setList(listAll);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/mybatis/v1/standard")
  public ResponseDto<ScientistResDto.ScientistList> standard() {
    List<ScientistVo> result = scientistDao.findAll();
    List<ScientistResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList());
    ScientistResDto.ScientistList resDto = new ScientistResDto.ScientistList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/mybatis/v1/primary")
  public ResponseDto<ScientistResDto.ScientistList> primary() {
    List<ScientistVo> result = scientistPrimaryDao.findAll();
    List<ScientistResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList());
    ScientistResDto.ScientistList resDto = new ScientistResDto.ScientistList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/mybatis/v1/h2")
  public ResponseDto<ScientistResDto.ScientistList> h2() {
    List<ScientistVo> result = scientistH2Dao.findAll();
    List<ScientistResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList());
    ScientistResDto.ScientistList resDto = new ScientistResDto.ScientistList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/mybatis/v1/maria")
  public ResponseDto<ScientistResDto.ScientistList> maria() {
    List<ScientistVo> result = scientistMariaDao.findAll();
    List<ScientistResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList());
    ScientistResDto.ScientistList resDto = new ScientistResDto.ScientistList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/mybatis/v1/oracle")
  public ResponseDto<ScientistResDto.ScientistList> oracle() {
    List<ScientistVo> result = scientistOracleDao.findAll();
    List<ScientistResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList());
    ScientistResDto.ScientistList resDto = new ScientistResDto.ScientistList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/mybatis/v1/postgres")
  public ResponseDto<ScientistResDto.ScientistList> postgres() {
    List<ScientistVo> result = scientistPostgresDao.findAll();
    List<ScientistResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList());
    ScientistResDto.ScientistList resDto = new ScientistResDto.ScientistList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
}
