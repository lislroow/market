package spring.app.market.api.delivery.repository;

import org.springframework.data.repository.Repository;

import spring.app.market.api.delivery.entity.Order;

public interface OrderRepository extends Repository<Order, Integer>{
  
  void save(Order param);
  
}
