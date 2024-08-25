package spring.app.market.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import spring.app.market.dto.ProductInventoryREQ;
import spring.app.market.service.InventoryService;

@RestController
public class InventoryController {
  
  private InventoryService service;
  
  public InventoryController(InventoryService service) {
    this.service = service;
  }
  
  @PutMapping("/api/market/inventory/update-qty")
  public void updateQty(@RequestBody List<ProductInventoryREQ> req) {
    service.updateQty(req);
  }
  
}
