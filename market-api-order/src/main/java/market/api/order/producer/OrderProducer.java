package market.api.order.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import market.lib.dto.OrderDTO;

@Component
@RequiredArgsConstructor
public class OrderProducer {
  
  private final KafkaTemplate<String, Object> template;
  
  public void send(OrderDTO order) {
    this.template.send("OrderService.process", order);
  }
  
}
