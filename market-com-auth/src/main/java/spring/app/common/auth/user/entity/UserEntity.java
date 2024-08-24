package spring.app.common.auth.user.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.framework.common.user.Role;
import spring.framework.enumcode.YN;

@Entity(name = "user")
@DynamicInsert
@DynamicUpdate
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
  
  @Id
  @Column(columnDefinition = "varchar(14)")
  private String id;
  
  @Column(columnDefinition = "varchar(255)")
  private String password;
  
  @Column(columnDefinition = "varchar(255)")
  private String email;
  
  @Column(columnDefinition = "varchar(50)")
  private String nickname;
  
  @Column(columnDefinition = "varchar(255)")
  private String picture;
  
  @Enumerated(EnumType.STRING)
  private Role role;
  
  
  @Enumerated(EnumType.STRING)
  private YN lockedYn;
  
  @Enumerated(EnumType.STRING)
  private YN dormantYn;
  
  @Column(columnDefinition = "datetime(6)")
  private LocalDateTime passwordExpireDate;

  @Column(columnDefinition = "varchar(10)")
  private String registrationId;
  
  @Column(columnDefinition = "varchar(100)")
  private String oauth2Id;
  
  @Column(columnDefinition = "datetime(6)")
  @ColumnDefault(value = "current_timestamp(6)")
  private LocalDateTime createDate;
  
  @Column(columnDefinition = "datetime(6)")
  @ColumnDefault(value = "current_timestamp(6)")
  private LocalDateTime modifyDate;
}
