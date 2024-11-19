package market.initial;

import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.stream.IntStream;

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
  
  public static final List<String> PRINT_JAR_LIST = Arrays.asList(
      "spring-boot",
      "spring-cloud-starter",
      "mybatis",
      "lombok-mapstruct-binding",
      "mapstruct",
      "modelmapper",
      "jasypt-spring-boot-starter",
      "log4jdbc-log4j2-jdbc4.1"
      );
  
  @EventListener
  public void applicationReady(ApplicationReadyEvent event) {
    printJars();
  }
  
  private void printJars() {
    List<BootJarVo> list = ClasspathLibs.getBootJars();
    int maxJarWidth = list.stream()
        .filter(item -> PRINT_JAR_LIST.contains(item.getJar()))
        .map(item -> item.getJar().length())
        .max(Integer::compareTo)
        .orElse(0);
    int maxVersionWidth = list.stream()
        .filter(item -> PRINT_JAR_LIST.contains(item.getJar()))
        .map(item -> item.getVersion().length())
        .max(Integer::compareTo)
        .orElse(0);
    List<AsciiTable.Column> columns = Arrays.asList(
        new AsciiTable.Column("jar", maxJarWidth),
        new AsciiTable.Column("ver", maxVersionWidth)
        );
    Formatter fmt = AsciiTable.header(columns);
    IntStream.range(0, list.size()-1)
      .forEach(i -> {
        if (list.get(i) == null) {
          return;
        }
        if (!PRINT_JAR_LIST.contains(list.get(i).getJar())) {
          return; 
        }
        AsciiTable.body(columns, new Object[] {
            list.get(i).getJar(),
            list.get(i).getVersion()
          }, fmt);
        });
    AsciiTable.space(columns, fmt);
    log.info("Application Info \n### Core Libraries\n{}", fmt);
  }
}
