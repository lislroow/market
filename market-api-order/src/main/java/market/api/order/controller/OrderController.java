package market.api.order.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import market.api.order.dto.OrderREQ;
import market.api.order.dto.OrderRES;
import market.api.order.service.OrderService;

@RestController
public class OrderController {
  
  private OrderService service;
  
  public OrderController(OrderService service) {
    this.service = service;
  }
  
  @PostMapping("/order/v1/process")
  public void orderProducts(@RequestBody OrderREQ req) {
    service.process(req);
  }
  
  @GetMapping("/order/v1/my-orders")
  public List<OrderRES> myOrder() {
    List<OrderRES> res = service.myOrders();
    return res;
  }
  
}
