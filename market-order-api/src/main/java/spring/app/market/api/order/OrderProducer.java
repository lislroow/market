package spring.app.market.api.order;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import spring.app.market.dto.order.OrderDTO;

@Component
public class OrderProducer {
  
  private KafkaTemplate<String, Object> template;
  
  public OrderProducer(KafkaTemplate<String, Object> template) {
    this.template = template;
  }
  
  public void send(OrderDTO order) {
    this.template.send("OrderService.process", order);
  }
  
}
