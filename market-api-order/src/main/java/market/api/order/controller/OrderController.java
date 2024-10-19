package market.api.order.controller;

import java.io.Serializable;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import market.api.order.dto.OrderReqDto;
import market.api.order.dto.OrderResDto;
import market.api.order.service.OrderService;
import market.lib.aop.annotation.Login;
import market.lib.aop.annotation.UserInfo;
import market.lib.dto.ResponseDto;
import market.lib.vo.UserVo;

@RestController
@RequiredArgsConstructor
public class OrderController {
  
  private final OrderService orderService;
  
  @PostMapping("/order/v1/process")
  public ResponseDto<Serializable> orderProducts(@RequestBody OrderReqDto.ItemReq request) {
    orderService.process(request);
    return ResponseDto.body();
  }
  
  @GetMapping("/order/v1/my/orders")
  @Login
  public ResponseDto<OrderResDto.ItemListRes> myOrder(@UserInfo UserVo user) {
    OrderResDto.ItemListRes resDto = new OrderResDto.ItemListRes();
    resDto.setList(orderService.myOrders(user));
    return ResponseDto.body(resDto);
  }
}
