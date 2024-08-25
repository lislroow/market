package spring.app.market.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import spring.app.market.dto.DeliveryRES;
import spring.app.market.service.DeliveryService;

@RestController
public class DeliveryController {
  
  private DeliveryService service;
  
  public DeliveryController(DeliveryService service) {
    this.service = service;
  }
  
  @GetMapping("/api/market/delivery/status/{orderId}")
  public List<DeliveryRES> statusOrder(
      @PathVariable(name = "orderId", required = true) Integer orderId) {
    List<DeliveryRES> resList = service.statusOrder(orderId);
    return resList;
  }
  
}
