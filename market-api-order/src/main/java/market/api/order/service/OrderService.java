package market.api.order.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import market.api.order.dto.OrderReqDto;
import market.api.order.dto.OrderResDto;
import market.api.order.entity.Customer;
import market.api.order.entity.Order;
import market.api.order.producer.OrderProducer;
import market.api.order.repository.CustomerRepository;
import market.api.order.repository.OrderRepository;
import market.lib.config.webmvc.SessionContext;
import market.lib.dto.kafka.OrderDto;
import market.lib.vo.SessionUser;
import market.lib.vo.User;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
  
  private final OrderRepository repository;
  private final CustomerRepository customerRepository;
  private final OrderProducer producer;
  private final ModelMapper model;
  
  @Transactional
  public void process(OrderReqDto.ItemReq request) {
    // persist
    User user = new User();
    user.setId("01ibc1wnncc6ld");
    SessionUser sessionUser = new SessionUser(user);
    Customer customer = customerRepository.findById(SessionContext.getUser().orElse(sessionUser).getId()).get();
    Order order = model.map(request, Order.class);
    order.setCustomer(customer);
    
    // 저장
    order = repository.save(order);
    OrderDto orderDto = model.map(order, OrderDto.class);
    
    this.producer.send(orderDto);
    // order 정상 처리 이벤트 등록 (kafka)
    //  - delivery 에서 배송 데이터 생성 
    //  - inventory 에서 재고 데이터 반영
  }
  
  public List<OrderResDto.ItemRes> myOrders() {
    Customer customer = customerRepository.findById(SessionContext.getUser().orElseThrow().getId()).get();
    List<Order> result = repository.findByCustomerId(customer.getId()).get();
    List<OrderResDto.ItemRes> listRES = result.stream()
        .map(item -> model.map(item, OrderResDto.ItemRes.class))
        .collect(Collectors.toList());
    return listRES;
  }
  
}
