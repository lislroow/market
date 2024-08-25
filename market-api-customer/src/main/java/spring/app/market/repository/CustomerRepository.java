package spring.app.market.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import spring.app.market.entity.Customer;

public interface CustomerRepository extends Repository<Customer, String> {
  
  List<Customer> saveAll(Iterable<Customer> param);
  
  Optional<Customer> findById(String id);
  
  Optional<Customer> save(Customer param);
  
  Optional<Customer> findByName(String name);
  
}
