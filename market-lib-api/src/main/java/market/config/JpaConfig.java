package market.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import lombok.RequiredArgsConstructor;
import market.common.constant.Constant;

@Configuration
@RequiredArgsConstructor
public class JpaConfig {
  
  final org.springframework.boot.autoconfigure.orm.jpa.JpaProperties jpaProperties;
  final DataSource dataSourcePrimary;
  
  @Bean
  LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSourcePrimary);
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
