package spring.app.market.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.app.market.common.user.SessionUser;
import spring.app.market.common.user.User;
import spring.app.market.config.webmvc.SessionContext;
import spring.app.market.dto.OrderREQ;
import spring.app.market.dto.OrderRES;
import spring.app.market.dto.order.OrderDTO;
import spring.app.market.entity.Customer;
import spring.app.market.entity.Order;
import spring.app.market.producer.OrderProducer;
import spring.app.market.repository.CustomerRepository;
import spring.app.market.repository.OrderRepository;

@Service
@Transactional(readOnly = true)
public class OrderService {
  
  private OrderRepository repository;
  private CustomerRepository customerRepository;
  private OrderProducer producer;
  
  @Autowired
  ModelMapper model;
  
  public OrderService(OrderRepository repository,
      CustomerRepository customerRepository,
      OrderProducer producer) {
    this.repository = repository;
    this.customerRepository = customerRepository;
    this.producer = producer;
  }
  
  @Transactional
  public void process(OrderREQ req) {
    // persist
    User user = new User();
    user.setId("01ibc1wnncc6ld");
    SessionUser sessionUser = new SessionUser(user);
    Customer customer = customerRepository.findById(SessionContext.getUser().orElse(sessionUser).getId()).get();
    Order order = model.map(req, Order.class);
    order.setCustomer(customer);
    
    // 저장
    order = repository.save(order);
    OrderDTO orderDTO = model.map(order, OrderDTO.class);
    
    this.producer.send(orderDTO);
    // order 정상 처리 이벤트 등록 (kafka)
    //  - delivery 에서 배송 데이터 생성 
    //  - inventory 에서 재고 데이터 반영
  }
  
  public List<OrderRES> myOrders() {
    Customer customer = customerRepository.findById(SessionContext.getUser().orElseThrow().getId()).get();
    List<Order> result = repository.findByCustomerId(customer.getId()).get();
    List<OrderRES> listRES = result.stream()
        .map(item -> model.map(item, OrderRES.class)).collect(Collectors.toList());
    return listRES;
  }
  
}