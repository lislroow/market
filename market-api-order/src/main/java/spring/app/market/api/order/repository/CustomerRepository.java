package spring.app.market.api.order.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import spring.app.market.api.order.entity.Customer;

public interface CustomerRepository extends Repository<Customer, String> {
  
  Optional<Customer> findById(String id);
  
  Optional<Customer> findByName(String name);
  
}
