package spring.app.market.api.customer;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import spring.app.market.api.customer.dto.CustomerDeliveryREQ;
import spring.app.market.api.customer.dto.CustomerDeliveryRES;
import spring.app.market.api.customer.dto.CustomerREQ;
import spring.app.market.api.customer.dto.CustomerRES;

@RestController
public class CustomerController {
  
  private CustomerService service;
  
  public CustomerController(CustomerService service) {
    this.service = service;
  }
  
  @GetMapping("/api/market/customer/my-info")
  public CustomerRES myInfo() {
    CustomerRES res = service.myInfo();
    return res;
  }
  
  @PutMapping("/api/market/customer/save-basic-info")
  public CustomerRES saveBasicInfo(@RequestBody CustomerREQ req) {
    CustomerRES res = service.saveBasicInfo(req);
    return res;
  }
  
  @GetMapping("/api/market/customer/my-delivery-address")
  public List<CustomerDeliveryRES> myDeliveryAddress() {
    List<CustomerDeliveryRES> res = service.myDeliveryAddress();
    return res;
  }
  
  @PutMapping("/api/market/customer/save-delivery-address")
  public List<CustomerDeliveryRES> saveDeliveryAddress(@RequestBody List<CustomerDeliveryREQ> req) {
    List<CustomerDeliveryRES> resList = service.saveDeliveryAddress(req);
    return resList;
  }
  
  @DeleteMapping("/api/market/customer/delete-delivery-address")
  public List<CustomerDeliveryRES> deleteDeliveryAddress(@RequestBody List<CustomerDeliveryREQ> req) {
    List<CustomerDeliveryRES> resList = service.deleteDeliveryAddress(req);
    return resList;
  }
  
  @PostMapping("/api/market/customer/init")
  public void init() {
    service.init();
  }
  
  @PostMapping("/api/market/customer/add")
  public void add() {
    service.add();
  }
}
