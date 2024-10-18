package market.api.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import market.api.product.entity.Product;

public interface ProductRepository extends Repository<Product, Integer> {
  
  List<Product> saveAll(Iterable<Product> param);
  Product save(Product param);
  
  List<Product> findAll();
  Optional<Product> findById(Integer param);
  
  Optional<Product> findByName(String param);
  
  Product delete(Product param);
}
