package market.api.order.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import market.common.dto.kafka.OrderDto;

@Component
@RequiredArgsConstructor
public class OrderProducer {
  
  private final KafkaTemplate<String, Object> template;
  
  public void send(OrderDto order) {
    this.template.send("OrderService.process", order);
  }
  
}
