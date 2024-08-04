package spring.app.market.api.product.repository;

import java.util.List;
import java.util.Optional;

//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import spring.app.market.api.product.entity.Product;

public interface ProductRepository extends Repository<Product, Integer> {
  
  List<Product> saveAll(Iterable<Product> param);
  Product save(Product param);
  
  Optional<List<Product>> findAll();
  Optional<Product> findById(Integer param);
  
  Optional<Product> findByName(String param);
  
  Product delete(Product param);
  
//  @Query("SELECT p FROM product p WHERE p.id IN :ids")
//  List<Product> findByIds(List<Integer> ids);
}
