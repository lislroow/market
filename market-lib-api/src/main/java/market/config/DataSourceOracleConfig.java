package market.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;
import market.common.constant.Constant;

@Configuration
@ConditionalOnProperty(
    prefix = "market.datasource."+Constant.DBMS.ORACLE + ".hikari", name = Constant.ENABLED,
    havingValue = "true",
    matchIfMissing = false)
@Slf4j
public class DataSourceOracleConfig {
  
  @Bean(name = Constant.DBMS.ORACLE + "DataSource")
  @ConfigurationProperties(prefix = "market.datasource."+Constant.DBMS.ORACLE+".hikari")
  DataSource dataSource() {
    HikariDataSource hikariDataSource = DataSourceBuilder.create()
        .type(HikariDataSource.class)
        .build();
    hikariDataSource.setPoolName("hikari-"+Constant.DBMS.ORACLE);
    return hikariDataSource;
  }
  
  @Bean(name = Constant.DBMS.ORACLE + "PlatformTransactionManager")
  PlatformTransactionManager transactionManager() {
    PlatformTransactionManager transactionManager = null;
    transactionManager = new DataSourceTransactionManager(dataSource());
    return transactionManager;
  }
  
  @Value("classpath:init-"+Constant.DBMS.ORACLE+".sql")
  private org.springframework.core.io.Resource initScript;
  
  @Bean(name = Constant.DBMS.ORACLE + "DataSourceInitializer")
  @ConditionalOnProperty(name = "market.datasource." + Constant.DBMS.ORACLE + ".init", havingValue = "true", matchIfMissing = false)
  DataSourceInitializer dataSourceInitializer() {
    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource());
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.addScript(initScript);
    initializer.setDatabasePopulator(populator);
    return initializer;
  }
}
