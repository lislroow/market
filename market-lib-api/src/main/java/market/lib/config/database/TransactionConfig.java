package market.lib.config.database;

import javax.sql.DataSource;

import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class TransactionConfig {

  final DataSource dataSource_primary;
  final AbstractEntityManagerFactoryBean entityManagerFactory;
  final MybatisProperties mybatisProperties;
  
  @Bean
  PlatformTransactionManager transactionManager() {
    DataSourceTransactionManager mybatisTransactionManager = null;
    if (mybatisProperties != null) {
      mybatisTransactionManager = new DataSourceTransactionManager();
      mybatisTransactionManager.setDataSource(dataSource_primary);
    }
    
    JpaTransactionManager jpaTransactionManager = null;
    jpaTransactionManager = new JpaTransactionManager();
    jpaTransactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
    
    PlatformTransactionManager transactionManager = null;
    transactionManager = jpaTransactionManager;
    return transactionManager;
  }
}
