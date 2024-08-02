package spring.app.common.elastic.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticConfig {
  
  @Bean
  RestHighLevelClient restHighLevelClient() {
    BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    credentialsProvider.setCredentials(AuthScope.ANY, 
        new UsernamePasswordCredentials("elastic", "trustno1"));
    
    RestClientBuilder builder = RestClient.builder(
        new HttpHost("localhost", 9200, "https"))
        .setHttpClientConfigCallback(clientBuilder -> 
          clientBuilder.setDefaultCredentialsProvider(credentialsProvider));
    
    return new RestHighLevelClient(builder);
  }
  
}
