package market.api.customer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import market.api.customer.dto.MyInfoReqDto;
import market.lib.enumcode.YN;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDelivery {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "int(7)")
  private Integer id;
  
  @Column(columnDefinition = "varchar(500)")
  private String address;
  
  @Enumerated(EnumType.STRING)
  private YN primaryYn;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  private Customer customer;
  
  public static CustomerDelivery create(Customer customer) {
    CustomerDelivery entity = new CustomerDelivery();
    entity.setCustomer(customer);
    return entity;
  }
  
  public CustomerDelivery update(MyInfoReqDto.DeliveryAddressReq request) {
    this.address = request.getAddress();
    this.primaryYn = request.getPrimaryYn();
    return this;
  }
}
