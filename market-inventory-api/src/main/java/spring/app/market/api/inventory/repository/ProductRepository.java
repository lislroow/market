package spring.app.market.api.inventory.repository;

import java.util.List;
import java.util.Optional;

//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import spring.app.market.api.inventory.entity.Product;

public interface ProductRepository extends Repository<Product, Integer> {
  
  List<Product> saveAll(Iterable<Product> param);
  Optional<Product> findById(Integer param);
  
}
