package market.api.customer.consumer;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import market.api.customer.entity.Customer;
import market.api.customer.repository.CustomerRepository;
import market.lib.vo.User;

@Component
@RequiredArgsConstructor
public class UserConsumer {
  
  private final CustomerRepository repository;
  
  @KafkaListener(topics = "CustomOAuth2UserService.loadUser", containerFactory = "kafkaListener")
  @Transactional
  public void loadUser(User user) {
    Customer entity = Customer.builder()
        .id(user.getId())
        .email(user.getEmail())
        .name(user.getNickname())
        .createDate(LocalDateTime.now())
        .modifyDate(LocalDateTime.now())
        .build();
    this.repository.save(entity);
  }
}
