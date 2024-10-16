package market.api.delivery.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import market.api.delivery.entity.Delivery;
import market.api.delivery.entity.id.DeliveryId;

public interface DeliveryRepository extends Repository<Delivery, DeliveryId> {
  
  Optional<List<Delivery>> findByOrderId(Integer param);
  
  List<Delivery> saveAll(Iterable<Delivery> param);
  
}
