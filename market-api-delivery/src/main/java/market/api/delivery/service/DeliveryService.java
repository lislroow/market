package market.api.delivery.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import market.api.delivery.dto.DeliveryRES;
import market.api.delivery.dto.OrderItemRES;
import market.api.delivery.entity.Delivery;
import market.api.delivery.repository.DeliveryRepository;

@Service
@Transactional(readOnly = true)
public class DeliveryService {
  
  private DeliveryRepository repository;
  
  @Autowired
  ModelMapper model;
  
  public DeliveryService(DeliveryRepository repository) {
    this.repository = repository;
  }
  
  public List<DeliveryRES> statusOrder(Integer orderId) {
    List<Delivery> entityList = repository.findByOrderId(orderId).get();
    List<DeliveryRES> resList = entityList.stream()
        .map(item -> {
          DeliveryRES res = DeliveryRES.create(item);
          res.setOrderItem(model.map(item.getOrderItem(), OrderItemRES.class));
          return res;
        })
        .collect(Collectors.toList());
    return resList;
  }
  
}
