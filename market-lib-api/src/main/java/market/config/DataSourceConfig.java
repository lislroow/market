package market.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;
import market.common.constant.Constant;

@Configuration
@ConditionalOnProperty(
    prefix = "spring.datasource.hikari", name = Constant.ENABLED,
    havingValue = "true",
    matchIfMissing = false)
@Slf4j
public class DataSourceConfig {

  @Bean(name = "dataSource")
  @ConfigurationProperties(prefix = "spring.datasource.hikari")
  DataSource dataSource() {
    HikariDataSource hikariDataSource = DataSourceBuilder.create()
        .type(HikariDataSource.class)
        .build();
    return hikariDataSource;
  }
  
  @Bean(name = "platformTransactionManager")
  PlatformTransactionManager transactionManager() {
    PlatformTransactionManager transactionManager = null;
    transactionManager = new DataSourceTransactionManager(dataSource());
    return transactionManager;
  }
}
