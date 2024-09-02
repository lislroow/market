package market.api.delivery.repository;

import org.springframework.data.repository.Repository;

import market.api.delivery.entity.Order;

public interface OrderRepository extends Repository<Order, Integer>{
  
  void save(Order param);
  
}
