package market.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;
import market.common.annotation.MapperH2;
import market.common.annotation.MapperMaria;
import market.common.annotation.MapperOracle;
import market.common.annotation.MapperPostgres;
import market.common.annotation.MapperVertica;
import market.common.bean.MybatisMapperNameGenerator;
import market.common.constant.Constant;
import market.common.enumcode.DBMS_TYPE;

@Configuration
@Slf4j
public class MapperScannerConfig {
  
  public static final String FILE_APPLICATION_PROPERTIES = "application.properties";
  public static final String PRIMARY_KEY = "market.mybatis.primary";
  
  static {
    Properties properties = new Properties();
    try (InputStream input = MapperScannerConfig.class
        .getClassLoader()
        .getResourceAsStream(FILE_APPLICATION_PROPERTIES)) {
      if (input == null) {
        log.error("'{}' file not exist", FILE_APPLICATION_PROPERTIES);
        throw new Error();
      }
      properties.load(input);
    } catch (IOException ex) {
      log.error("'{}' load fail", FILE_APPLICATION_PROPERTIES);
    }
    String primary = properties.getProperty(PRIMARY_KEY);
    
    if (ObjectUtils.isEmpty(primary)) {
      log.warn("'{}' property not found", PRIMARY_KEY);
    } else {
      DBMS_TYPE.setPrimary(primary);
    }
  }

  @Bean(name = Constant.DBMS.H2 + "MapperScannerConfigurer")
  MapperScannerConfigurer h2MapperScannerConfigurer() {
    DBMS_TYPE dbmsType = DBMS_TYPE.H2;
    MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
    scannerConfigurer.setBasePackage(Constant.BASE_PACKAGE);
    scannerConfigurer.setSqlSessionFactoryBeanName(dbmsType.sqlSessionFactoryBeanName());
    scannerConfigurer.setNameGenerator(new MybatisMapperNameGenerator(dbmsType));
    scannerConfigurer.setAnnotationClass(MapperH2.class);
    return scannerConfigurer;
  }
  
  @Bean(Constant.DBMS.MARIA + "MapperScannerConfigurer")
  MapperScannerConfigurer mariaMapperScannerConfigurer() {
    DBMS_TYPE dbmsType = DBMS_TYPE.MARIA;
    MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
    scannerConfigurer.setBasePackage(Constant.BASE_PACKAGE);
    scannerConfigurer.setSqlSessionFactoryBeanName(dbmsType.sqlSessionFactoryBeanName());
    scannerConfigurer.setNameGenerator(new MybatisMapperNameGenerator(dbmsType));
    scannerConfigurer.setAnnotationClass(MapperMaria.class);
    return scannerConfigurer;
  }
  
  @Bean(Constant.DBMS.ORACLE + "MapperScannerConfigurer")
  MapperScannerConfigurer oracleMapperScannerConfigurer() {
    DBMS_TYPE dbmsType = DBMS_TYPE.ORACLE;
    MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
    scannerConfigurer.setBasePackage(Constant.BASE_PACKAGE);
    scannerConfigurer.setSqlSessionFactoryBeanName(dbmsType.sqlSessionFactoryBeanName());
    scannerConfigurer.setNameGenerator(new MybatisMapperNameGenerator(dbmsType));
    scannerConfigurer.setAnnotationClass(MapperOracle.class);
    return scannerConfigurer;
  }
  
  @Bean(Constant.DBMS.VERTICA + "MapperScannerConfigurer")
  MapperScannerConfigurer verticaMapperScannerConfigurer() {
    DBMS_TYPE dbmsType = DBMS_TYPE.VERTICA;
    MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
    scannerConfigurer.setBasePackage(Constant.BASE_PACKAGE);
    scannerConfigurer.setSqlSessionFactoryBeanName(dbmsType.sqlSessionFactoryBeanName());
    scannerConfigurer.setNameGenerator(new MybatisMapperNameGenerator(dbmsType));
    scannerConfigurer.setAnnotationClass(MapperVertica.class);
    return scannerConfigurer;
  }
  
  @Bean(Constant.DBMS.POSTGRES + "MapperScannerConfigurer")
  MapperScannerConfigurer postgresMapperScannerConfigurer() {
    DBMS_TYPE dbmsType = DBMS_TYPE.POSTGRES;
    MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
    scannerConfigurer.setBasePackage(Constant.BASE_PACKAGE);
    scannerConfigurer.setSqlSessionFactoryBeanName(dbmsType.sqlSessionFactoryBeanName());
    scannerConfigurer.setNameGenerator(new MybatisMapperNameGenerator(dbmsType));
    scannerConfigurer.setAnnotationClass(MapperPostgres.class);
    return scannerConfigurer;
  }
  
}