package market.api.delivery.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import market.api.delivery.dto.DeliveryResDto;
import market.api.delivery.service.DeliveryService;
import market.common.dto.ResponseDto;

@RestController
@RequiredArgsConstructor
public class DeliveryController {
  
  private final DeliveryService deliveryService;
  
  @GetMapping("/delivery/v1/status/{orderId}")
  public ResponseDto<ArrayList<DeliveryResDto.StatusRes>> getOrder(
      @PathVariable Integer orderId) {
    return ResponseDto.body(new ArrayList<>(deliveryService.getOrder(orderId)));
  }
}
