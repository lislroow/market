package market.api.delivery.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import market.api.delivery.dto.DeliveryRES;
import market.api.delivery.service.DeliveryService;
import market.lib.dto.ResponseDto;

@RestController
@RequiredArgsConstructor
public class DeliveryController {
  
  private final DeliveryService deliveryService;
  
  @GetMapping("/delivery/v1/status/{orderId}")
  public ResponseDto<List<DeliveryRES>> getOrder(
      @PathVariable Integer orderId) {
    List<DeliveryRES> resList = deliveryService.getOrder(orderId);
    return ResponseDto.body(resList);
  }
  
}
