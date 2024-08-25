package spring.app.market.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import spring.app.market.entity.Customer;

public interface CustomerRepository extends Repository<Customer, String> {
  
  Optional<Customer> findById(String id);
  
  Optional<Customer> findByName(String name);
  
}
