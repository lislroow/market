package market.api.customer.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import market.api.customer.dto.CustomerDto;
import market.api.customer.feign.UserControllerFeign;
import market.api.customer.service.CustomerService;

@RestController
@RequiredArgsConstructor
public class CustomerController {
  
  private final CustomerService service;
  private final UserControllerFeign userControllerFeign;
  
  @GetMapping("/customer/v1/my/info")
  public CustomerDto.InfoRes getMyInfo() throws Exception {
    CustomerDto.InfoRes res = service.myInfo();
    List<String> result = userControllerFeign.test();
    System.out.println(result);
    return res;
  }
  
  @PutMapping("/customer/v1/my/info")
  public CustomerDto.InfoRes saveMyInfo(@RequestBody CustomerDto.InfoReq req) {
    CustomerDto.InfoRes res = service.saveBasicInfo(req);
    return res;
  }
  
  @GetMapping("/customer/v1/my/delivery-address")
  public List<CustomerDto.DeliveryRes> myDeliveryAddress() {
    List<CustomerDto.DeliveryRes> res = service.myDeliveryAddress();
    return res;
  }
  
  @PutMapping("/customer/v1/my/delivery-address")
  public List<CustomerDto.DeliveryRes> saveDeliveryAddress(@RequestBody List<CustomerDto.DeliveryReq> req) {
    List<CustomerDto.DeliveryRes> resList = service.saveDeliveryAddress(req);
    return resList;
  }
  
  @DeleteMapping("/customer/v1/my/delivery-address")
  public List<CustomerDto.DeliveryRes> deleteDeliveryAddress(@RequestBody List<CustomerDto.DeliveryReq> req) {
    List<CustomerDto.DeliveryRes> resList = service.deleteDeliveryAddress(req);
    return resList;
  }
  
  @PostMapping("/customer/v1/init")
  public void init() {
    service.init();
  }
  
  @PostMapping("/customer/v1/add")
  public void add() {
    service.add();
  }
}
