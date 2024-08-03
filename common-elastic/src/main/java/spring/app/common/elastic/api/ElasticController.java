package spring.app.common.elastic.api;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ElasticController {
  
  private RestHighLevelClient client;
  
  @GetMapping("/hello")
  public String hello() throws Exception {
    SearchRequest request = new SearchRequest("fibonacci");
    SearchSourceBuilder builder = new SearchSourceBuilder();
    
    String[] includeFields = new String[] { "days", "num" };
    String[] excludeFields = new String[] { };
    builder.fetchSource(includeFields, excludeFields);
    
    builder.sort("days", SortOrder.ASC);
    builder.from(0);
    //builder.size(3);
    
    //builder.query(QueryBuilders.matchAllQuery());
    builder.query(QueryBuilders.rangeQuery("days").from(3).to(6));
    request.source(builder);
    
    SearchResponse response = client.search(request, RequestOptions.DEFAULT);
    return response.toString();
  }
  
}
