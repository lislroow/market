package spring.app.market.consumer;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import spring.app.market.dto.order.OrderDTO;
import spring.app.market.entity.Delivery;
import spring.app.market.entity.Order;
import spring.app.market.entity.id.DeliveryId;
import spring.app.market.repository.DeliveryRepository;
import spring.app.market.repository.OrderRepository;

@Slf4j
@Component
public class OrderConsumer {
  
  DeliveryRepository repository;
  OrderRepository orderRepository;
  
  public OrderConsumer(DeliveryRepository repository,
      OrderRepository orderRepository) {
    this.repository = repository;
    this.orderRepository = orderRepository;
  }
  
  @Autowired
  ModelMapper model;
  
  @KafkaListener(topics = "OrderService.process", containerFactory = "kafkaListener")
  public void orderProcess(OrderDTO orderDTO) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      String str = mapper.writeValueAsString(orderDTO);
      log.info("str={}", str);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    Order orderEntity = model.map(orderDTO, Order.class);
    orderEntity.getOrderItems().forEach(item -> item.setOrder(orderEntity));
    orderRepository.save(orderEntity);
    
    List<Delivery> entityList = orderDTO.getOrderItems().stream()
      .map(item -> {
        return Delivery.builder()
            .receiverName(orderDTO.getReceiverName())
            .receiverAddress(orderDTO.getReceiverAddress())
            .id(DeliveryId.builder()
                .orderId(orderDTO.getId())
                .orderItemId(item.getId())
                .build())
            .build();
      }).collect(Collectors.toList());
    repository.saveAll(entityList);
  }
}
