package market.api.customer.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import market.api.customer.dto.MyInfoReqDto;
import market.api.customer.dto.MyInfoResDto;
import market.api.customer.entity.Customer;
import market.api.customer.entity.CustomerDelivery;
import market.api.customer.repository.MyDeliveryRepository;
import market.api.customer.repository.MyInfoRepository;
import market.lib.config.webmvc.SessionContext;
import market.lib.vo.UserVo;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyInfoService {
  
  final MyInfoRepository myInfoRepository;
  final MyDeliveryRepository myDeliveryRepository;
  final ModelMapper model;
  
  public MyInfoResDto.UserRes getUser(UserVo user) {
    Customer entity = myInfoRepository.findByEmail(user.getUserId()).orElseThrow();
    return model.map(entity, MyInfoResDto.UserRes.class);
  }
  
  @Transactional
  public MyInfoResDto.UserRes saveMyInfo(
      UserVo user,
      MyInfoReqDto.UserReq request) {
    Customer entity = myInfoRepository.findById(user.getUserId()).orElseThrow();
    entity.saveBasicInfo(request);
    entity = myInfoRepository.save(entity).orElseThrow();
    return model.map(entity, MyInfoResDto.UserRes.class);
  }
  
  public List<MyInfoResDto.DeliveryAddressRes> getDeliveryAddress(
      UserVo user) {
    String customerId = user.getUserId();
    List<CustomerDelivery> entityList = myDeliveryRepository.findByCustomerId(customerId);
    return entityList.stream()
        .map(item -> model.map(item, MyInfoResDto.DeliveryAddressRes.class))
        .toList();
  }
  
  @Transactional
  public List<MyInfoResDto.DeliveryAddressRes> saveDeliveryAddress(
      UserVo user,
      List<MyInfoReqDto.DeliveryAddressReq> request) {
    String customerId = SessionContext.getUser().orElseThrow().getId();
    Customer customer = myInfoRepository.findById(customerId).orElseThrow();
    List<CustomerDelivery> entityList = request.stream()
        .map(item -> {
          CustomerDelivery entity = model.map(item, CustomerDelivery.class);
          entity.setCustomer(customer);
          return entity;
        })
        .toList();
    entityList = myDeliveryRepository.saveAll(entityList);
    return entityList.stream()
        .map(item -> model.map(item, MyInfoResDto.DeliveryAddressRes.class))
        .toList();
  }
  
  @Transactional
  public List<MyInfoResDto.DeliveryAddressRes> deleteDeliveryAddress(
      UserVo user,
      List<MyInfoReqDto.DeliveryAddressReq> request) {
    List<CustomerDelivery> entityList = request.stream()
        .map(item -> this.myDeliveryRepository.findById(item.getId()).get())
        .toList();
    entityList = myDeliveryRepository.deleteAll(entityList);
    return entityList.stream()
        .map(item -> model.map(item, MyInfoResDto.DeliveryAddressRes.class))
        .toList();
  }
  
}
