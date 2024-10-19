package market.api.customer.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import market.api.customer.dto.MyInfoReqDto;
import market.api.customer.dto.MyInfoResDto;
import market.api.customer.service.MyInfoService;
import market.lib.aop.annotation.Login;
import market.lib.aop.annotation.UserInfo;
import market.lib.dto.ResponseDto;
import market.lib.vo.UserVo;

@RestController
@RequiredArgsConstructor
public class MyInfoController {
  
  private final MyInfoService myInfoService;
  
  @GetMapping("/customer/v1/my/user")
  @Login
  public ResponseDto<MyInfoResDto.UserRes> getUser(@UserInfo UserVo user) {
    return ResponseDto.body(myInfoService.getUser(user));
  }
  
  @PutMapping("/customer/v1/my/user")
  @Login
  public ResponseDto<MyInfoResDto.UserRes> saveUser(
      @UserInfo UserVo user,
      @RequestBody MyInfoReqDto.UserReq request) {
    return ResponseDto.body(myInfoService.saveMyInfo(user, request));
  }

  @GetMapping("/customer/v1/my/delivery-address")
  @Login
  public ResponseDto<List<MyInfoResDto.DeliveryAddressRes>> getDeliveryAddress(
      @UserInfo UserVo user) {
    List<MyInfoResDto.DeliveryAddressRes> res = myInfoService.getDeliveryAddress(user);
    return ResponseDto.body(res);
  }

  @PutMapping("/customer/v1/my/delivery-address")
  @Login
  public ResponseDto<List<MyInfoResDto.DeliveryAddressRes>> saveDeliveryAddress(
      @UserInfo UserVo user,
      @RequestBody List<MyInfoReqDto.DeliveryAddressReq> request) {
    List<MyInfoResDto.DeliveryAddressRes> resList = myInfoService.saveDeliveryAddress(user, request);
    return ResponseDto.body(resList);
  }

  @DeleteMapping("/customer/v1/my/delivery-address")
  @Login
  public ResponseDto<List<MyInfoResDto.DeliveryAddressRes>> deleteDeliveryAddress(
      @UserInfo UserVo user,
      @RequestBody List<MyInfoReqDto.DeliveryAddressReq> request) {
    List<MyInfoResDto.DeliveryAddressRes> resList = myInfoService.deleteDeliveryAddress(user, request);
    return ResponseDto.body(resList);
  }
}
