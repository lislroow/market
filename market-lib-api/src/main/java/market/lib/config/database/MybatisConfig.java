package market.lib.config.database;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;
import market.lib.config.database.mybatis.DaoSupport;

@Configuration
@RequiredArgsConstructor
public class MybatisConfig {
  
  Logger log = LoggerFactory.getLogger(MybatisConfig.class);
  
  
  final MybatisProperties mybatisProperties;
  
  final DataSource dataSourcePrimary;
  
  // primary
  @Bean(name = "sqlSessionFactoryBeanPrimary")
  @Primary
  SqlSessionFactoryBean sqlSessionFactoryBeanPrimary() {
    String typeAliasesPackage = mybatisProperties.getTypeAliasesPackage();
    log.info("[primary] typeAliasesPackage: {}", typeAliasesPackage);
    
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSourcePrimary);
    
    // mybatis.xml 을 사용할 경우
    sqlSessionFactoryBean.setMapperLocations(mybatisProperties.resolveMapperLocations());
    // parameterType, resultType 에 java package 생략 가능
    // 단, BASE_PACKAGES 하위 package 에 동일한 클래스명이 2개 이상일 경우 어플리케이션 booting 시 오류가 발생함
    if (!ObjectUtils.isEmpty(typeAliasesPackage)) {
      sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
    }
    
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
  DaoSupport daoSupportPrimary(
      @Qualifier("sqlSessionTemplatePrimary") SqlSessionTemplate sqlSessionTemplate) {
    return new DaoSupport(sqlSessionTemplate);
  }
  
  @Bean(name = "sqlSessionTemplatePrimary")
  @Primary
  SqlSessionTemplate sqlSessionTemplatePrimary(
      @Qualifier("sqlSessionFactoryBeanPrimary") SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
     return new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
  }
  
  @Bean(name = "sqlSessionTemplateForBatchExecutorPrimary")
  SqlSessionTemplate sqlSessionTemplateForBatchExecutorPrimary(
      @Qualifier("sqlSessionFactoryBeanPrimary") SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
    return new SqlSessionTemplate(sqlSessionFactoryBean.getObject(), ExecutorType.BATCH);
  }
}
