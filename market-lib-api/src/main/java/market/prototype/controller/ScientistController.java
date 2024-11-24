package market.prototype.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import market.common.dto.ResponseDto;
import market.common.mybatis.Pageable;
import market.common.mybatis.Paged;
import market.common.mybatis.PagedList;
import market.prototype.dao.ScientistDao;
import market.prototype.dto.ScientistReqDto;
import market.prototype.dto.ScientistResDto;
import market.prototype.service.ScientistService;
import market.prototype.vo.ScientistVo;

@RestController
@RequiredArgsConstructor
public class ScientistController {

  final ModelMapper modelMapper;
  final ScientistService scientistService;
  final ScientistDao scientistDao;
  
  @GetMapping("/mybatis/v1/scientists")
  public ResponseDto<ScientistResDto.ScientistList> findAll() {
    
    List<ScientistVo> result = scientistDao.findAll();
    ScientistResDto.ScientistList resDto = new ScientistResDto.ScientistList();
    List<ScientistResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList());
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/mybatis/v1/scientist/{id}")
  public ResponseDto<ScientistResDto.Scientist> findById(
      @PathVariable String id) {
    
    ScientistVo result = scientistDao.findById(id);
    ScientistResDto.Scientist resDto = modelMapper.map(result, ScientistResDto.Scientist.class);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/mybatis/v1/scientist")
  public ResponseDto<ScientistResDto.PagedScientistList> findList(
      Pageable pageable) {
    
    PagedList<ScientistVo> result = scientistDao.findList(pageable);
    List<ScientistResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList());
    Paged paged = modelMapper.map(result, Paged.class);
    ScientistResDto.PagedScientistList resDto = new ScientistResDto.PagedScientistList();
    resDto.setPaged(paged);
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/mybatis/v1/scientist/list/{name}")
  public PagedList<ScientistVo> findListByName(
      @PathVariable String name, Pageable pageable) {
    
    ScientistVo.FindVo vo = ScientistVo.FindVo.builder()
        .name(name)
        .build();
    vo.setPageable(pageable);
    PagedList<ScientistVo> result = scientistDao.findListByName(vo);
    return result;
  }
  
  @PostMapping("/mybatis/v1/scientist")
  public ResponseDto<ScientistResDto.Scientist> add(
      @RequestBody ScientistReqDto.AddDto reqDto) {
    
    ScientistVo.AddVo vo = modelMapper.map(reqDto, ScientistVo.AddVo.class);
    ScientistVo result = scientistService.add(vo);
    ScientistResDto.Scientist resDto = modelMapper.map(result, ScientistResDto.Scientist.class);
    return ResponseDto.body(resDto);
  }
  
  @PutMapping("/mybatis/v1/scientist")
  public ResponseDto<?> modifyNameById(
      @RequestBody ScientistReqDto.ModifyDto reqDto) {
    
    ScientistVo.ModifyVo vo = modelMapper.map(reqDto, ScientistVo.ModifyVo.class);
    scientistService.modifyNameById(vo);
    return ResponseDto.body();
  }
  
  @DeleteMapping("/mybatis/v1/scientist/{id}")
  public ResponseDto<?> removeById(
      @PathVariable String id) {
    
    ScientistVo.RemoveVo vo = ScientistVo.RemoveVo.builder()
        .id(id)
        .build();
    scientistService.removeById(vo);
    return ResponseDto.body();
  }
  
}
