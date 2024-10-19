package market.api.delivery.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import market.api.delivery.dto.DeliveryResDto;
import market.api.delivery.entity.Delivery;
import market.api.delivery.repository.DeliveryRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeliveryService {
  
  private final DeliveryRepository repository;
  private final ModelMapper model;
  
  public List<DeliveryResDto.StatusRes> getOrder(Integer orderId) {
    List<Delivery> entityList = repository.findByOrderId(orderId).orElseThrow();
    return entityList.stream()
        .map(item -> {
          DeliveryResDto.StatusRes res = DeliveryResDto.StatusRes.create(item);
          res.setOrderItem(model.map(item.getOrderItem(), DeliveryResDto.OrderItemRes.class));
          return res;
        })
        .toList();
  }
  
}
