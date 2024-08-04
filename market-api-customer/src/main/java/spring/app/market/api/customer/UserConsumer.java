package spring.app.market.api.customer;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import spring.app.market.api.customer.entity.Customer;
import spring.app.market.api.customer.repository.CustomerRepository;
import spring.framework.common.user.User;

@Slf4j
@Component
public class UserConsumer {
  
  private CustomerRepository repository;
  
  public UserConsumer(CustomerRepository repository) {
    this.repository = repository;
  }
  
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