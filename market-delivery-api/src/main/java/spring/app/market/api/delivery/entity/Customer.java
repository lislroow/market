package spring.app.market.api.delivery.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
  
  @Id
  @Column(columnDefinition = "varchar(14)")
  private String id;
  
  @Column(columnDefinition = "varchar(50)")
  private String name;
  
  @Column(columnDefinition = "varchar(255)")
  private String email;
  
  @Column(columnDefinition = "datetime(6)")
  @ColumnDefault(value = "current_timestamp(6)")
  private LocalDateTime createDate;
  
  @Column(columnDefinition = "datetime(6)", nullable = false)
  @ColumnDefault(value = "current_timestamp(6)")
  private LocalDateTime modifyDate;
  
}
