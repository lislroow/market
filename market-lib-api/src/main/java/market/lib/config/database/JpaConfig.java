package market.lib.config.database;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import market.lib.constant.Constant;

@Configuration
public class JpaConfig {
  
  @Autowired
  org.springframework.boot.autoconfigure.orm.jpa.JpaProperties jpaProperties;
  
  //@Autowired
  //LazyConnectionDataSourceProxy dataSource;
  
  @Autowired
  //@Qualifier("dataSource_primary")
  DataSource dataSource_primary;
  
  @Bean
  LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean em 
      = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource_primary);
    em.setPackagesToScan(Constant.BASE_PACKAGE);
    
    AbstractJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    
    vendorAdapter.setGenerateDdl(jpaProperties.isGenerateDdl());
    vendorAdapter.setShowSql(this.jpaProperties.isShowSql());
    Properties prop = new Properties();
    prop.putAll(this.jpaProperties.getProperties());
    em.setJpaProperties(prop);
    return em;
  }
  
}
