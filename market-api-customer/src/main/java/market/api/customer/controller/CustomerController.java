package market.api.customer.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import market.api.customer.dto.CustomerDeliveryREQ;
import market.api.customer.dto.CustomerDeliveryRES;
import market.api.customer.dto.CustomerREQ;
import market.api.customer.dto.CustomerRES;
import market.api.customer.service.CustomerService;

@RestController
public class CustomerController {
  
  private CustomerService service;
  
  public CustomerController(CustomerService service) {
    this.service = service;
  }
  
  @GetMapping("/customer/v1/my-info")
  public CustomerRES myInfo() {
    CustomerRES res = service.myInfo();
    return res;
  }
  
  @PutMapping("/customer/v1/save-basic-info")
  public CustomerRES saveBasicInfo(@RequestBody CustomerREQ req) {
    CustomerRES res = service.saveBasicInfo(req);
    return res;
  }
  
  @GetMapping("/customer/v1/my-delivery-address")
  public List<CustomerDeliveryRES> myDeliveryAddress() {
    List<CustomerDeliveryRES> res = service.myDeliveryAddress();
    return res;
  }
  
  @PutMapping("/customer/v1/save-delivery-address")
  public List<CustomerDeliveryRES> saveDeliveryAddress(@RequestBody List<CustomerDeliveryREQ> req) {
    List<CustomerDeliveryRES> resList = service.saveDeliveryAddress(req);
    return resList;
  }
  
  @DeleteMapping("/customer/v1/delete-delivery-address")
  public List<CustomerDeliveryRES> deleteDeliveryAddress(@RequestBody List<CustomerDeliveryREQ> req) {
    List<CustomerDeliveryRES> resList = service.deleteDeliveryAddress(req);
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
