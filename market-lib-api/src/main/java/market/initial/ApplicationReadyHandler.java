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
  
  @EventListener
  public void applicationReady(ApplicationReadyEvent event) {
    printJars();
  }
  
  private void printJars() {
    List<AsciiTable.Column> columns = Arrays.asList(
        new AsciiTable.Column("jar", 35),
        new AsciiTable.Column("version", 12)
        );
    Formatter fmt = AsciiTable.header(columns);
    List<BootJarVo> list = ClasspathLibs.getBootJars();
    IntStream.range(0, list.size()-1)
      .forEach(i -> {
        if (!list.get(i).getJar().equals("spring-boot") && 
            !list.get(i).getJar().equals("spring-cloud-starter")) {
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
