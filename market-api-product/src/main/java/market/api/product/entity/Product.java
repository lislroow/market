package market.api.product.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "int(10)")
  private Integer id;
  
  @Column(columnDefinition = "varchar(500)")
  private String name;
  
  @Column(columnDefinition = "varchar(2000)")
  private String imgThumbUrl;
  
  @Column(columnDefinition = "datetime(6)")
  @ColumnDefault(value = "current_timestamp(6)")
  private LocalDateTime createDate;
  
  @Column(columnDefinition = "datetime(6)", nullable = false)
  @ColumnDefault(value = "current_timestamp(6)")
  private LocalDateTime modifyDate;
  
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(
    name = "product_category_mapping",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id")
  )
  private List<ProductCategory> categories = new ArrayList<ProductCategory>();
  
  @OneToOne(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private ProductInventory inventory;
}
