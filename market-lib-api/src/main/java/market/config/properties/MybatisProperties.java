package market.config.properties;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.ibatis.type.JdbcType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import lombok.Data;
import market.common.enumcode.DBMS_TYPE;

@ConfigurationProperties(prefix = "market.mybatis", ignoreUnknownFields = true)
@Data
public class MybatisProperties {
  
  private DBMS_TYPE primary;
  private Configure h2;
  private Configure maria;
  private Configure oracle;
  private Configure vertica;
  private Configure postgres;
  
  public Configure getConfigure(DBMS_TYPE dbmsType) {
    switch (dbmsType) {
    case H2:
      return h2;
    case MARIA:
      return maria;
    case ORACLE:
      return oracle;
    case VERTICA:
      return vertica;
    case POSTGRES:
      return postgres;
    }
    return null;
  }
  
  @Data
  public static class Configure {
    private Boolean enabled;
    private String mapperLocations;
    private String typeAliasesPackage;
    private Boolean mapUnderscoreToCamelCase;
    private JdbcType jdbcTypeForNull;
    private Integer defaultStatementTimeout;
    private Boolean cacheEnabled;
    
    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
    
    public Resource[] resolveMapperLocations() {
      return Stream.of(Optional.ofNullable(this.mapperLocations).orElse(new String()))
          .flatMap(location -> Stream.of(getResources(location)))
          .toArray(Resource[]::new);
    }
    
    private Resource[] getResources(String location) {
      try {
        return resourceResolver.getResources(location);
      } catch (IOException e) {
        return new Resource[0];
      }
    }
  }
}
