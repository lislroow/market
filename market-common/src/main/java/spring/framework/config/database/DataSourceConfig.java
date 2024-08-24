package spring.framework.config.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.sql.DataSource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//import org.springframework.util.Assert;
//
//import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DataSourceConfig {
  
  Logger log = LoggerFactory.getLogger(DataSourceConfig.class);
  
//  @Bean(name = "dataSource_primary")
//  @ConfigurationProperties(prefix = "framework.database.datasource")
//  @Primary
//  DataSource dataSource() throws SQLException {
//    return DataSourceBuilder.create()
//        .type(HikariDataSource.class)
//        .build();
//  }
//  
//  @Bean
//  RoutingDataSource routingDataSource(
//      @Qualifier("dataSource_primary") DataSource dataSource_primary) {
//    RoutingDataSource routingDataSource = new RoutingDataSource();
//    Map<Object, Object> dataSources = new HashMap<Object, Object>();
//    dataSources.put("primary", dataSource_primary);
//    routingDataSource.setTargetDataSources(dataSources);
//    routingDataSource.setDefaultTargetDataSource(dataSource_primary);
//    return routingDataSource;
//  }
//  
//  class RoutingDataSource extends AbstractRoutingDataSource {
//    private DataSource datasource;
//    private boolean lenientFallback = true;
//    @Override
//    protected DataSource determineTargetDataSource() {
//      Assert.notNull(getResolvedDataSources(), "DataSource router not initialized");
//      Object lookupKey = determineCurrentLookupKey();
//      DataSource dataSource = getResolvedDataSources().get(lookupKey);
//      if (dataSource == null && (lenientFallback || lookupKey == null)) {
//        dataSource = getResolvedDefaultDataSource();
//        log.info("defaultTarget: " + ((HikariDataSource) dataSource).getJdbcUrl());
//      } else {
//        log.info("determineTarget: " + ((HikariDataSource) dataSource).getJdbcUrl());
//      }
//      return dataSource;
//    }
//    
//    void setFallbackTargetDataSource(DataSource datasource) {
//      this.datasource = datasource;
//    }
//    
//    @Override
//    protected Object determineCurrentLookupKey() {
//      // 특정 사용자, 특정 서비스 등에 따라 datasource 가 결정되도록 할 수 있습니다. 
//      // primary datasource 를 기본값으로 설정했습니다.
//      String routingKey = "primary";
//      return routingKey;
//    }
//    
//    @Override
//    public Connection getConnection() throws SQLException {
//      Connection conn = determineTargetDataSource().getConnection();
//      return conn;
//    }
//  }
//  
//  @Bean(name = "lazyConnectionDataSourceProxy")
//  LazyConnectionDataSourceProxy lazyConnectionDataSourceProxy(
//      RoutingDataSource routingDataSource) {
//    return new LazyConnectionDataSourceProxy(routingDataSource);
//  }
}
