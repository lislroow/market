package market.api.inventory.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import market.api.inventory.dto.InventoryReqDto;
import market.api.inventory.service.InventoryService;
import market.lib.dto.kafka.ResponseDto;

@RestController
@RequiredArgsConstructor
public class InventoryController {
  
  private final InventoryService inventoryService;
  
  @PutMapping("/inventory/v1/qty")
  public ResponseDto<?> qty(@RequestBody List<InventoryReqDto.UpdateQty> request) {
    inventoryService.updateQty(request);
    return ResponseDto.body();
  }
  
}
