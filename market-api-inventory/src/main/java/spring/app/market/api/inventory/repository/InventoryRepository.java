package spring.app.market.api.inventory.repository;

import org.springframework.data.repository.Repository;

import spring.app.market.api.inventory.entity.ProductInventory;

public interface InventoryRepository extends Repository<ProductInventory, Integer>{
  
  public ProductInventory findById(Integer param);
  
  public void save(ProductInventory param);
  
}