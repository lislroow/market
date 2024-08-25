package spring.app.market.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.app.market.entity.id.DeliveryId;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
  
  @JsonIgnore
  @EmbeddedId
  private DeliveryId id;
  
  @Column(columnDefinition = "varchar(50)")
  private String receiverName;
  
  @Column(columnDefinition = "varchar(500)")
  private String receiverAddress;
  
  @JsonIgnoreProperties({"delivery"})
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", insertable = false, updatable = false)
  private Order order;
  
  @JsonIgnoreProperties({"delivery"})
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_item_id", insertable = false, updatable = false)
  private OrderItem orderItem;
}
