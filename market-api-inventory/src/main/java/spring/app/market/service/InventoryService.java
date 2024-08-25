package spring.app.market.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.app.market.dto.ProductInventoryREQ;
import spring.app.market.entity.ProductInventory;
import spring.app.market.repository.InventoryRepository;
import spring.app.market.repository.ProductRepository;

@Service
@Transactional(readOnly = true)
public class InventoryService {
  
  private InventoryRepository repository;
  
  @Autowired
  ModelMapper model;
  
  public InventoryService(InventoryRepository repository,
      ProductRepository productRepository) {
    this.repository = repository;
  }
  
  @Transactional
  public void updateQty(List<ProductInventoryREQ> req) {
    req.stream().forEach(item -> {
      ProductInventory entity = repository.findById(item.getId());
      entity.updateQty(item);
      repository.save(entity);
    });
  }
  
}