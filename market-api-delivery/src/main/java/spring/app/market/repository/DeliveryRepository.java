package spring.app.market.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import spring.app.market.entity.Delivery;
import spring.app.market.entity.id.DeliveryId;

public interface DeliveryRepository extends Repository<Delivery, DeliveryId> {
  
  Optional<List<Delivery>> findByOrderId(Integer param);
  
  List<Delivery> saveAll(Iterable<Delivery> param);
  
}
