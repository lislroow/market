package market.api.customer.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import market.api.customer.dto.CustomerReqDto;
import market.api.customer.dto.CustomerResDto;
import market.api.customer.service.CustomerService;
import market.lib.dto.ResponseDto;

@RestController
@RequiredArgsConstructor
public class CustomerController {
  
  private final CustomerService customerService;
  //private final UserControllerFeign userControllerFeign;
  
  @GetMapping("/customer/v1/my/info")
  public ResponseDto<CustomerResDto.InfoRes> getMyInfo() throws Exception {
    String a = "hello";
    System.out.println(a);
    return ResponseDto.body(customerService.myInfo());
  }
  
  @PutMapping("/customer/v1/my/info")
  public ResponseDto<CustomerResDto.InfoRes> saveMyInfo(
      @RequestBody CustomerReqDto.InfoReq request) {
    return ResponseDto.body(customerService.saveBasicInfo(request));
  }
  
  @GetMapping("/customer/v1/my/delivery-address")
  public ResponseDto<List<CustomerResDto.DeliveryRes>> myDeliveryAddress() {
    List<CustomerResDto.DeliveryRes> res = customerService.myDeliveryAddress();
    return ResponseDto.body(res);
  }
  
  @PutMapping("/customer/v1/my/delivery-address")
  public ResponseDto<List<CustomerResDto.DeliveryRes>> saveDeliveryAddress(
      @RequestBody List<CustomerReqDto.DeliveryReq> request) {
    List<CustomerResDto.DeliveryRes> resList = customerService.saveDeliveryAddress(request);
    return ResponseDto.body(resList);
  }
  
  @DeleteMapping("/customer/v1/my/delivery-address")
  public ResponseDto<List<CustomerResDto.DeliveryRes>> deleteDeliveryAddress(
      @RequestBody List<CustomerReqDto.DeliveryReq> request) {
    List<CustomerResDto.DeliveryRes> resList = customerService.deleteDeliveryAddress(request);
    return ResponseDto.body(resList);
  }
  
  @PostMapping("/customer/v1/init")
  public ResponseDto<?> init() {
    customerService.init();
    return ResponseDto.body();
  }
  
  @PostMapping("/customer/v1/add")
  public ResponseDto<?> add() {
    customerService.add();
    return ResponseDto.body();
  }
}
