package market.api.order.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import market.api.order.dto.OrderReqDto;
import market.api.order.dto.OrderResDto;
import market.api.order.service.OrderService;
import market.lib.dto.kafka.ResponseDto;

@RestController
@RequiredArgsConstructor
public class OrderController {
  
  private final OrderService orderService;
  
  @PostMapping("/order/v1/process")
  public ResponseDto<?> orderProducts(@RequestBody OrderReqDto.ItemReq request) {
    orderService.process(request);
    return ResponseDto.body();
  }
  
  @GetMapping("/order/v1/my/orders")
  public ResponseDto<OrderResDto.ItemListRes> myOrder() {
    OrderResDto.ItemListRes resDto = new OrderResDto.ItemListRes();
    resDto.setList(orderService.myOrders());
    return ResponseDto.body(resDto);
  }
}
