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
import market.config.conditions.MybatisH2EnableCondition;
import market.config.properties.MybatisProperties;

@Configuration
@Conditional(MybatisH2EnableCondition.class)
@EnableConfigurationProperties(market.config.properties.MybatisProperties.class)
@Slf4j
public class MybatisH2Config {
  
  @Autowired
  market.config.properties.MybatisProperties mybatisProperties;
  
  @Autowired
  @Qualifier(value = Constant.DBMS.H2 + "DataSource")
  DataSource dataSource;
  
  @Bean(name = Constant.DBMS.H2 + Constant.BEAN.SQL_SESSION_FACTORY_BEAN)
  SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
    MybatisProperties.Configure config = mybatisProperties.getConfigure(DBMS_TYPE.H2);
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
  
  @Bean(name = Constant.DBMS.H2 + "SqlSessionTemplate")
  SqlSessionTemplate sqlSessionTemplate() throws Exception {
     return new SqlSessionTemplate(sqlSessionFactoryBean().getObject());
  }
  
  @Bean(name = Constant.DBMS.H2 + "SqlSessionTemplateForBatchExecutor")
  SqlSessionTemplate sqlSessionTemplateForBatchExecutor() throws Exception {
    return new SqlSessionTemplate(sqlSessionFactoryBean().getObject(), ExecutorType.BATCH);
  }
  
  @Bean(name = Constant.DBMS.H2 + "DaoSupport")
  DaoSupport daoSupport() throws Exception {
    return new DaoSupport(sqlSessionTemplate());
  }
}
