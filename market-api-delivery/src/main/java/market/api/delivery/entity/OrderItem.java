package market.api.delivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class OrderItem {
  
  @Id
  @Column(columnDefinition = "int(8)")
  private Integer id;
  
  @Column(columnDefinition = "int(6)")
  private Integer orderQty;
  
  @JsonIgnoreProperties({"orderItem"})
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;
  
  @JsonIgnoreProperties({"orderItem"})
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;
  
  @JsonIgnoreProperties({"orderItem"})
  @OneToOne(mappedBy = "orderItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Delivery delivery;
}
