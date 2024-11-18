package market.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;
import market.common.constant.Constant;
import market.common.enumcode.DBMS_TYPE;
import market.common.mybatis.DaoSupport;
import market.common.mybatis.PagingInterceptor;
import market.config.conditions.MybatisVerticaEnableCondition;
import market.config.properties.MybatisProperties;

@Configuration
@Conditional(MybatisVerticaEnableCondition.class)
@EnableConfigurationProperties(market.config.properties.MybatisProperties.class)
@Slf4j
public class MybatisVerticaConfig {
  
  @Autowired
  market.config.properties.MybatisProperties mybatisProperties;
  
  @Autowired
  @Qualifier(value = Constant.DBMS.VERTICA + "DataSource")
  DataSource dataSource;
  
  @Bean(name = Constant.DBMS.VERTICA + Constant.BEAN.SQL_SESSION_FACTORY_BEAN)
  SqlSessionFactoryBean sqlSessionFactoryBean() {
    MybatisProperties.Configure config = mybatisProperties.getConfigure(DBMS_TYPE.VERTICA);
    String typeAliasesPackage = config.getTypeAliasesPackage();
    log.info("[mybatis] mybatis.type-aliases-package: {}", typeAliasesPackage);
    
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource);
    sqlSessionFactoryBean.setMapperLocations(config.resolveMapperLocations());
    if (!ObjectUtils.isEmpty(typeAliasesPackage)) {
      sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
    }
    
    org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
    configuration.setJdbcTypeForNull(config.getJdbcTypeForNull());
    configuration.setMapUnderscoreToCamelCase(config.getMapUnderscoreToCamelCase());
    sqlSessionFactoryBean.setConfiguration(configuration);
    
    sqlSessionFactoryBean.setPlugins(new PagingInterceptor());
    return sqlSessionFactoryBean;
  }
  
  @Bean(name = Constant.DBMS.VERTICA + "SqlSessionTemplate")
  SqlSessionTemplate sqlSessionTemplate() throws Exception {
     return new SqlSessionTemplate(sqlSessionFactoryBean().getObject());
  }
  
  @Bean(name = Constant.DBMS.VERTICA + "SqlSessionTemplateForBatchExecutor")
  SqlSessionTemplate sqlSessionTemplateForBatchExecutor() throws Exception {
    return new SqlSessionTemplate(sqlSessionFactoryBean().getObject(), ExecutorType.BATCH);
  }
  
  @Bean(name = Constant.DBMS.VERTICA + "DaoSupport")
  DaoSupport daoSupport() throws Exception {
    return new DaoSupport(sqlSessionTemplate());
  }
}
