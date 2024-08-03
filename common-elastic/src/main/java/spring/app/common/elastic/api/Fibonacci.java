package spring.app.common.elastic.api;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(indexName = "fibonacci")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fibonacci {

  @Id
  private Integer days;
  private Long num;
  
}
