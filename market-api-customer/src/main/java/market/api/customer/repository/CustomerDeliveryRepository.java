package market.api.customer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import market.api.customer.entity.CustomerDelivery;

public interface CustomerDeliveryRepository extends Repository<CustomerDelivery, Integer> {

  Optional<CustomerDelivery> findById(Integer param);
  
  List<CustomerDelivery> findByCustomerId(String param);
  
  List<CustomerDelivery> saveAll(Iterable<CustomerDelivery> param);
  
  List<CustomerDelivery> deleteAll(Iterable<CustomerDelivery> param);
  
}
