package spring.app.market.config.webmvc;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
//@ConfigurationProperties(prefix = "framework.webmvc")
public class WebMvcProperties {

//  Jsp jsp = new Jsp();
//  @Data
//  class Jsp {
//    private Boolean enabled;
//    private String prefix;
//    private String suffix;
//  }
//
//  Tomcat tomcat = new Tomcat();
//  @Data
//  class Tomcat {
//    private Boolean enabled;
//    private Integer port = Integer.valueOf(8080);
//    private Valve valve = new Valve();
//    
//    @Data
//    class Valve {
//      private AccessLog accessLog = new AccessLog();
//      
//      @Data
//      class AccessLog {
//        private String pattern = "%{X-Forwarded-For}i %h %l %u %t \"%{Referer}i\" \"%r\" %s %b";
//        private String directory = "/logs";
//        private String prefix = "access";
//        private String suffix = ".log";
//        private boolean rotatable = false;
//      }
//    }
//  }
}
