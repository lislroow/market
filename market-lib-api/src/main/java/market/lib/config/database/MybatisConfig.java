package market.lib.config.database;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;
import market.lib.config.database.mybatis.DaoSupport;

@Slf4j
@Configuration
public class MybatisConfig {
  
  Logger log = LoggerFactory.getLogger(MybatisConfig.class);
  
  
  @Autowired
  MybatisProperties mybatisProperties;
  
  //@Autowired
  //@Qualifier("lazyConnectionDataSourceProxy")
  //LazyConnectionDataSourceProxy lazyConnectionDataSourceProxy;
  
  @Autowired
  //@Qualifier("dataSource_primary")
  DataSource dataSource_primary;
  
  // primary
  @Bean(name = "sqlSessionFactoryBean_primary")
  @Primary
  SqlSessionFactoryBean sqlSessionFactoryBean_primary() throws Exception {
    String typeAliasesPackage = mybatisProperties.getTypeAliasesPackage();
    log.info("[primary] typeAliasesPackage: " + typeAliasesPackage);
    
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    // RoutingDataSource 로 runtime 시 datasource 를 결정해야 할 경우 아래 코드 사용
    // sqlSessionFactoryBean.setDataSource(lazyConnectionDataSourceProxy);
    sqlSessionFactoryBean.setDataSource(dataSource_primary);
    
    // mybatis.xml 을 사용할 경우
    sqlSessionFactoryBean.setMapperLocations(mybatisProperties.resolveMapperLocations());
    // parameterType, resultType 에 java package 생략 가능
    // 단, BASE_PACKAGES 하위 package 에 동일한 클래스명이 2개 이상일 경우 어플리케이션 booting 시 오류가 발생함
    if (!ObjectUtils.isEmpty(typeAliasesPackage)) {
      sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
    }
    // mybatis.xml 은 application.properties 의 mybatis 설정으로 대체
    //sqlSessionFactoryBean.setConfigurationProperties(mybatisProperties.getConfigurationProperties());
    // TODO 적용 안됨
    //mybatisProperties.getConfiguration().applyTo((sqlSessionFactoryBean.getObject()).getConfiguration());
    
    // ---
    org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
    configuration.setJdbcTypeForNull(JdbcType.NULL);
    configuration.setMapUnderscoreToCamelCase(true);
    sqlSessionFactoryBean.setConfiguration(configuration);
    // ---
    
    // 페이징 처리를 위한 mybatis-plugin 추가
    sqlSessionFactoryBean.setPlugins(new market.lib.config.database.mybatis.PagingInterceptor());
    return sqlSessionFactoryBean;
  }
  
  @Bean
  @Primary
  DaoSupport daoSupport_primary(
      @Qualifier("sqlSessionTemplate_primary") SqlSessionTemplate sqlSessionTemplate) throws Exception {
    return new DaoSupport(sqlSessionTemplate);
  }
  
  @Bean(name = "sqlSessionTemplate_primary")
  @Primary
  SqlSessionTemplate sqlSessionTemplate_primary(
      @Qualifier("sqlSessionFactoryBean_primary") SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
     return new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
  }
  
  @Bean(name = "sqlSessionTemplateForBatchExecutor_primary")
  SqlSessionTemplate sqlSessionTemplateForBatchExecutor_primary(
      @Qualifier("sqlSessionFactoryBean_primary") SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
    return new SqlSessionTemplate(sqlSessionFactoryBean.getObject(), ExecutorType.BATCH);
  }
}
