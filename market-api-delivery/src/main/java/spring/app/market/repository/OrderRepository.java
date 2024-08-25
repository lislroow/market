package spring.app.market.repository;

import org.springframework.data.repository.Repository;

import spring.app.market.entity.Order;

public interface OrderRepository extends Repository<Order, Integer>{
  
  void save(Order param);
  
}
