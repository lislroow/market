package spring.app.common.elastic.api;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ElasticService {
  
  @NonNull
  private ElasticsearchOperations elasticOper;
  
  public Long findByDays(Integer days) {
    Criteria criteria = Criteria.where("days").is(days);
    CriteriaQuery query = new CriteriaQuery(criteria);
    SearchHits<Fibonacci> hits = elasticOper.search(query, Fibonacci.class);
    return hits.getSearchHit(0).getContent().getNum();
  }

}
