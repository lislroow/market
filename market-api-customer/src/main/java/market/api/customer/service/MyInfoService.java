package market.api.customer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class MyInfoService {
  
  private MyInfoRepository myInfoRepository;
  private MyDeliveryRepository myDeliveryRepository;
  
  @Autowired
  ModelMapper model;
  
  public MyInfoService(MyInfoRepository repository,
      MyDeliveryRepository myDeliveryRepository) {
    this.myInfoRepository = repository;
    this.myDeliveryRepository = myDeliveryRepository;
  }
  
  public MyInfoResDto.UserRes getUser(UserVo user) {
    Customer entity = myInfoRepository.findByEmail(user.getUserId()).get();
    MyInfoResDto.UserRes res = model.map(entity, MyInfoResDto.UserRes.class);
    return res;
  }
  
  @Transactional
  public MyInfoResDto.UserRes saveMyInfo(
      UserVo user,
      MyInfoReqDto.UserReq request) {
    Customer entity = myInfoRepository.findById(SessionContext.getUser().orElseThrow().getId()).get();
    entity.saveBasicInfo(request);
    
    entity = myInfoRepository.save(entity).orElseThrow();
    
    MyInfoResDto.UserRes res = model.map(entity, MyInfoResDto.UserRes.class);
    return res;
  }
  
  public List<MyInfoResDto.DeliveryAddressRes> getDeliveryAddress(UserVo user) {
    String customerId = SessionContext.getUser().orElseThrow().getId();
    List<CustomerDelivery> entityList = myDeliveryRepository.findByCustomerId(customerId);
    List<MyInfoResDto.DeliveryAddressRes> res = entityList.stream()
        .map(item -> model.map(item, MyInfoResDto.DeliveryAddressRes.class))
        .collect(Collectors.toList());
    return res;
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
        .collect(Collectors.toList());
    entityList = myDeliveryRepository.saveAll(entityList);
    List<MyInfoResDto.DeliveryAddressRes> res = entityList.stream()
        .map(item -> model.map(item, MyInfoResDto.DeliveryAddressRes.class))
        .collect(Collectors.toList());
    return res;
  }
  
  @Transactional
  public List<MyInfoResDto.DeliveryAddressRes> deleteDeliveryAddress(
      UserVo user,
      List<MyInfoReqDto.DeliveryAddressReq> request) {
    List<CustomerDelivery> entityList = request.stream()
        .map(item -> this.myDeliveryRepository.findById(item.getId()).get())
        .collect(Collectors.toList());
    entityList = myDeliveryRepository.deleteAll(entityList);
    List<MyInfoResDto.DeliveryAddressRes> res = entityList.stream()
        .map(item -> model.map(item, MyInfoResDto.DeliveryAddressRes.class))
        .collect(Collectors.toList());
    return res;
  }
  
  //@Transactional
  //public void init() {
  //  Customer 홍길동 = myInfoRepository.findByName("홍길동").orElse(Customer.builder().id(Uuid.create()).build());
  //  홍길동.setName("홍길동");
  //  홍길동.setEmail("gdhong@gmail.com");
  //  홍길동.setModifyDate(LocalDateTime.now());
  //  Customer 김개발 = myInfoRepository.findByName("김개발").orElse(Customer.builder().id(Uuid.create()).build());
  //  김개발.setName("김개발");
  //  김개발.setEmail("devkim@gmail.com");
  //  김개발.setModifyDate(LocalDateTime.now());
  //  
  //  CustomerDelivery 선유로 = CustomerDelivery.builder().customer(홍길동).address("서울시 선유로2").primaryYn(YN.Y).build();
  //  CustomerDelivery 양평로 = CustomerDelivery.builder().customer(김개발).address("서울시 양평로2").primaryYn(YN.Y).build();
  //  CustomerDelivery 국채보상로 = CustomerDelivery.builder().customer(김개발).address("대구시 국채보상로2").primaryYn(YN.N).build();
  //  홍길동.setDeliveries(Arrays.asList(선유로));
  //  김개발.setDeliveries(Arrays.asList(양평로, 국채보상로));
  //  
  //  myInfoRepository.saveAll(Arrays.asList(홍길동, 김개발));
  //}
  //
  //@Transactional
  //public void add() {
  //  String uid = Uuid.create();
  //  Customer dummy = myInfoRepository.findByName(uid).orElse(Customer.builder().id(uid).build());
  //  dummy.setName("홍길동-"+uid);
  //  dummy.setEmail(uid+"@gmail.com");
  //  dummy.setModifyDate(LocalDateTime.now());
  //  myInfoRepository.save(dummy);
  //}
  
}
