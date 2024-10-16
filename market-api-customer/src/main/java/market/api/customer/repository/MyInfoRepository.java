package market.api.customer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import market.api.customer.entity.Customer;

public interface MyInfoRepository extends Repository<Customer, String> {
  
  List<Customer> saveAll(Iterable<Customer> param);
  
  Optional<Customer> findById(String id);
  Optional<Customer> findByEmail(String email);
  
  Optional<Customer> save(Customer param);
  
  Optional<Customer> findByName(String name);
  
}
