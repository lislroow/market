package spring.app.market.config.security;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import spring.app.market.common.user.User;

@Component
public class UserProducer {
  
  private KafkaTemplate<String, Object> template;
  
  public UserProducer(KafkaTemplate<String, Object> template) {
    this.template = template;
  }
  
  public void send(User user) {
    this.template.send("CustomOAuth2UserService.loadUser", user);
  }
  
}
