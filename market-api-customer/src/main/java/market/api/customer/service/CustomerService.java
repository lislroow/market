package market.api.customer.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import market.api.customer.dto.CustomerDeliveryREQ;
import market.api.customer.dto.CustomerDeliveryRES;
import market.api.customer.dto.CustomerREQ;
import market.api.customer.dto.CustomerRES;
import market.api.customer.entity.Customer;
import market.api.customer.entity.CustomerDelivery;
import market.api.customer.repository.CustomerDeliveryRepository;
import market.api.customer.repository.CustomerRepository;
import market.lib.config.webmvc.SessionContext;
import market.lib.enumcode.YN;
import market.lib.util.Uuid;

@Service
@Transactional(readOnly = true)
public class CustomerService {
  
  private CustomerRepository repository;
  private CustomerDeliveryRepository customerDeliveryRepository;
  
  @Autowired
  ModelMapper model;
  
  public CustomerService(CustomerRepository repository,
      CustomerDeliveryRepository customerDeliveryRepository) {
    this.repository = repository;
    this.customerDeliveryRepository = customerDeliveryRepository;
  }
  
  public CustomerRES myInfo() {
    Customer entity = repository.findById(SessionContext.getUser().orElseThrow().getId()).get();
    CustomerRES res = model.map(entity, CustomerRES.class);
    return res;
  }
  
  @Transactional
  public CustomerRES saveBasicInfo(CustomerREQ req) {
    Customer entity = repository.findById(SessionContext.getUser().orElseThrow().getId()).get();
    entity.saveBasicInfo(req);
    
    entity = repository.save(entity).orElseThrow();
    
    CustomerRES res = model.map(entity, CustomerRES.class);
    return res;
  }
  
  public List<CustomerDeliveryRES> myDeliveryAddress() {
    String customerId = SessionContext.getUser().orElseThrow().getId();
    List<CustomerDelivery> entityList = customerDeliveryRepository.findByCustomerId(customerId);
    List<CustomerDeliveryRES> res = entityList
        .stream().map(item -> model.map(item, CustomerDeliveryRES.class))
        .collect(Collectors.toList());
    return res;
  }
  
  @Transactional
  public List<CustomerDeliveryRES> saveDeliveryAddress(List<CustomerDeliveryREQ> req) {
    String customerId = SessionContext.getUser().orElseThrow().getId();
    Customer customer = repository.findById(customerId).orElseThrow();
    List<CustomerDelivery> entityList = req.stream()
        .map(item -> {
          CustomerDelivery entity = model.map(item, CustomerDelivery.class);
          entity.setCustomer(customer);
          return entity;
        })
        .collect(Collectors.toList());
    entityList = customerDeliveryRepository.saveAll(entityList);
    List<CustomerDeliveryRES> res = entityList
        .stream().map(item -> model.map(item, CustomerDeliveryRES.class))
        .collect(Collectors.toList());
    return res;
  }
  
  @Transactional
  public List<CustomerDeliveryRES> deleteDeliveryAddress(List<CustomerDeliveryREQ> req) {
    List<CustomerDelivery> entityList = req.stream().map(item -> {
      return this.customerDeliveryRepository.findById(item.getId()).get();
    }).collect(Collectors.toList());
    entityList = customerDeliveryRepository.deleteAll(entityList);
    List<CustomerDeliveryRES> res = entityList
        .stream().map(item -> model.map(item, CustomerDeliveryRES.class))
        .collect(Collectors.toList());
    return res;
  }
  
  @Transactional
  public void init() {
    Customer 홍길동 = repository.findByName("홍길동").orElse(Customer.builder().id(Uuid.create()).build());
    홍길동.setName("홍길동");
    홍길동.setEmail("gdhong@gmail.com");
    홍길동.setModifyDate(LocalDateTime.now());
    Customer 김개발 = repository.findByName("김개발").orElse(Customer.builder().id(Uuid.create()).build());
    김개발.setName("김개발");
    김개발.setEmail("devkim@gmail.com");
    김개발.setModifyDate(LocalDateTime.now());
    
    CustomerDelivery 선유로 = CustomerDelivery.builder().customer(홍길동).address("서울시 선유로2").primaryYn(YN.Y).build();
    CustomerDelivery 양평로 = CustomerDelivery.builder().customer(김개발).address("서울시 양평로2").primaryYn(YN.Y).build();
    CustomerDelivery 국채보상로 = CustomerDelivery.builder().customer(김개발).address("대구시 국채보상로2").primaryYn(YN.N).build();
    홍길동.setDeliveries(Arrays.asList(선유로));
    김개발.setDeliveries(Arrays.asList(양평로, 국채보상로));
    
    repository.saveAll(Arrays.asList(홍길동, 김개발));
  }
  
  @Transactional
  public void add() {
    String uid = Uuid.create();
    Customer dummy = repository.findByName(uid).orElse(Customer.builder().id(uid).build());
    dummy.setName("홍길동-"+uid);
    dummy.setEmail(uid+"@gmail.com");
    dummy.setModifyDate(LocalDateTime.now());
    repository.save(dummy);
  }
  
}
