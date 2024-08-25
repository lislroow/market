package market.lib.config.database;

import javax.sql.DataSource;

import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class TransactionConfig {

  //@Autowired
  //LazyConnectionDataSourceProxy dataSource;
  
  @Autowired
  //@Qualifier("dataSource_primary")
  DataSource dataSource_primary;

  @Autowired(required = false)
  AbstractEntityManagerFactoryBean entityManagerFactory;
  
  @Autowired(required = false)
  MybatisProperties mybatisProperties;
  
  @Bean
  PlatformTransactionManager transactionManager() throws Exception {
    DataSourceTransactionManager mybatisTransactionManager = null;
    if (mybatisProperties != null) {
      mybatisTransactionManager = new DataSourceTransactionManager();
      mybatisTransactionManager.setDataSource(dataSource_primary);
    }
    
    JpaTransactionManager jpaTransactionManager = null;
    jpaTransactionManager = new JpaTransactionManager();
    jpaTransactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
    
    PlatformTransactionManager transactionManager = null;
//    if (mybatisProperties != null && jpaProperties != null) {
//      transactionManager = new ChainedTransactionManager(
//          mybatisTransactionManager,
//          jpaTransactionManager);
//    } else if (mybatisProperties != null) {
//      transactionManager = mybatisTransactionManager;
//    } else if (jpaProperties != null) {
//      transactionManager = jpaTransactionManager;
//    } else {
//      transactionManager = new DataSourceTransactionManager(dataSource);
//    }
    transactionManager = jpaTransactionManager;
    return transactionManager;
  }
}
