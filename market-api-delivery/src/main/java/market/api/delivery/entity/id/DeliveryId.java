package market.api.delivery.entity.id;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DeliveryId implements Serializable {
  
  private static final long serialVersionUID = 4807747429028502956L;

  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(columnDefinition = "int(8)")
  private Integer id;

  @Column(columnDefinition = "int(8)", name = "order_id")
  private Integer orderId;

  @Column(columnDefinition = "int(8)", name = "order_item_id")
  private Integer orderItemId;
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DeliveryId that = (DeliveryId) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(orderId, that.orderId) &&
        Objects.equals(orderItemId, that.orderItemId);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(id, orderId, orderItemId);
  }
}