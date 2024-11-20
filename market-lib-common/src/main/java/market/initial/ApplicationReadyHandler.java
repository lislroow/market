package market.initial;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import market.common.runtime.ClasspathLibs;
import market.common.util.AsciiTable;
import market.common.vo.BootJarVo;

@Component
@Slf4j
public class ApplicationReadyHandler {
  
  public static final List<String> CORE_LIST = Arrays.asList(
      "spring-boot",
      "spring-cloud-starter",
      "mybatis",
      "lombok-mapstruct-binding",
      "mapstruct",
      "modelmapper",
      "jasypt-spring-boot-starter",
      "log4jdbc-log4j2-jdbc4.1"
      );
  
  public static final List<String> JDBC_LIST = Arrays.asList(
      "h2",
      "mariadb-java-client",
      "ojdbc8",
      "vertica-jdbc",
      "postgresql"
      );
  
  @EventListener
  public void applicationReady(ApplicationReadyEvent event) {
    List<BootJarVo> list = ClasspathLibs.getBootJars();
    List<BootJarVo> coreList = list.stream()
        .filter(item -> CORE_LIST.contains(item.getJar()))
        .toList();
    log.info("Application Info # Core Libraries\n{}", AsciiTable.getTable(coreList, "jar", "ver"));
    
    List<BootJarVo> jdbcList = list.stream()
        .filter(item -> JDBC_LIST.contains(item.getJar()))
        .toList();
    log.info("Application Info # JDBC Libraries\n{}", AsciiTable.getTable(jdbcList, "jar", "ver"));
  }
}
