package spring.framework.config.database;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(basePackages = JpaConfig.BASE_PACKAGES)
public class JpaConfig {
  
  public static final String BASE_PACKAGES = "spring";
  
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
    em.setPackagesToScan(JpaConfig.BASE_PACKAGES);
    
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
