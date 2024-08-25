package spring.app.market.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Product {
  
  @Id
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

}
