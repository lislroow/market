package market.api.inventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import market.api.inventory.dto.ProductInventoryREQ;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInventory {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", referencedColumnName = "id")
  private Product product;
  
  @Column(columnDefinition = "int(5)")
  private Integer totalQty;
  
  //@Column(columnDefinition = "int(5)")
  //private Integer soldQty;
  
  public ProductInventory updateQty(ProductInventoryREQ req) {
    this.totalQty = req.getTotalQty();
    //this.soldQty = req.getSoldQty();
    return this;
  }
}
