package market.api.inventory.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import market.api.inventory.dto.ProductInventoryREQ;
import market.api.inventory.service.InventoryService;

@RestController
public class InventoryController {
  
  private InventoryService service;
  
  public InventoryController(InventoryService service) {
    this.service = service;
  }
  
  @PutMapping("/inventory/v1/update-qty")
  public void updateQty(@RequestBody List<ProductInventoryREQ> req) {
    service.updateQty(req);
  }
  
}
