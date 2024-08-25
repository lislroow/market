package spring.app.market.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import spring.app.market.dto.OrderREQ;
import spring.app.market.dto.OrderRES;
import spring.app.market.service.OrderService;

@RestController
public class OrderController {
  
  private OrderService service;
  
  public OrderController(OrderService service) {
    this.service = service;
  }
  
  @PostMapping("/api/market/order/process")
  public void orderProducts(@RequestBody OrderREQ req) {
    service.process(req);
  }
  
  @GetMapping("/api/market/order/my-orders")
  public List<OrderRES> myOrder() {
    List<OrderRES> res = service.myOrders();
    return res;
  }
  
}
