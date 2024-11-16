package market.api.delivery.consumer;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import market.api.delivery.entity.Delivery;
import market.api.delivery.entity.Order;
import market.api.delivery.entity.id.DeliveryId;
import market.api.delivery.repository.DeliveryRepository;
import market.api.delivery.repository.OrderRepository;
import market.common.dto.kafka.OrderDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConsumer {
  
  private final DeliveryRepository repository;
  private final OrderRepository orderRepository;
  private final ModelMapper model;
  
  @KafkaListener(topics = "OrderService.process", containerFactory = "kafkaListener")
  public void orderProcess(OrderDto orderDto) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      String str = mapper.writeValueAsString(orderDto);
      log.info("str={}", str);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    Order orderEntity = model.map(orderDto, Order.class);
    orderEntity.getOrderItems().forEach(item -> item.setOrder(orderEntity));
    orderRepository.save(orderEntity);
    
    List<Delivery> entityList = orderDto.getOrderItems().stream()
      .map(item ->
        Delivery.builder()
          .receiverName(orderDto.getReceiverName())
          .receiverAddress(orderDto.getReceiverAddress())
          .id(DeliveryId.builder()
              .orderId(orderDto.getId())
              .orderItemId(item.getId())
              .build())
          .build()
      ).toList();
    repository.saveAll(entityList);
  }
}
