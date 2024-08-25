package spring.app.market.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import spring.app.market.entity.Order;

public interface OrderRepository extends Repository<Order, Integer>{
  
  Optional<Order> findById(Order param);
  
  Optional<List<Order>> findByCustomerId(String customerId);
  
  List<Order> saveAll(Iterable<Order> param);
  
  Order save(Order param);
}
