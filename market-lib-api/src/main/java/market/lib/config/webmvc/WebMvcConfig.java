package market.lib.config.webmvc;

import java.util.Collection;

import org.apache.catalina.Valve;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.valves.AccessLogValve;
import org.apache.catalina.valves.HealthCheckValve;
import org.apache.catalina.valves.RemoteIpValve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@EnableConfigurationProperties({WebMvcProperties.class})
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  
//  @Autowired
//  WebMvcProperties properties;
  
  // TODO
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("GET", "POST", "PUT", "DELETE")
        .allowedHeaders("XAT", "XRT", "X-FORWARDED-FOR"
            , "Content-Type", "Accept-Language", "Referer")
        //.allowCredentials(true)
        .maxAge(3600); //  설정 seconds 동안 pre-flight 요청 caching 
  }
  
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    //registry.addViewController("/").setViewName("redirect:/jsp/main");
  }
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/webjars/**")
            .addResourceLocations("/webjars/")
            .resourceChain(false);
    registry.setOrder(1);
  }
  
//  @Bean
//  @ConditionalOnExpression(
//      "${framework.webmvc.jsp.enable:true}")
//  ViewResolver internalResourceViewResolver() {
//    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//    viewResolver.setPrefix(properties.getJsp().getPrefix());
//    viewResolver.setSuffix(properties.getJsp().getSuffix());
//    return viewResolver;
//  }
  
//  @Bean
//  @ConditionalOnExpression(
//      "${framework.webmvc.tomcat.enable:true}")
//  TomcatServletWebServerFactory tomcatServletWebServerFactory() {
//    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//      @Override
//      protected void customizeConnector(Connector connector) {
//        super.customizeConnector(connector);
//        connector.setMaxParameterCount(-1);
//        connector.setMaxPostSize(-1);
//        //connector.setProxyName("localhost");
//        
//        int port = properties.getTomcat().getPort();
//        // 임시 코드
//        if (System.getProperty("server.port") != null) {
//          port = Integer.parseInt(System.getProperty("server.port").toString());
//        }
//        // -- 임시 코드
//        connector.setPort(port);
//      }
//    };
//    
//    AccessLogValve accessLogValve = new AccessLogValve();
//    accessLogValve.setPattern(properties.getTomcat().getValve().getAccessLog().getPattern());
//    accessLogValve.setDirectory(properties.getTomcat().getValve().getAccessLog().getDirectory());
//    accessLogValve.setPrefix(properties.getTomcat().getValve().getAccessLog().getPrefix());
//    accessLogValve.setSuffix(properties.getTomcat().getValve().getAccessLog().getSuffix());
//    accessLogValve.setRotatable(properties.getTomcat().getValve().getAccessLog().isRotatable());
//    
//    HealthCheckValve healthCheckValve = new HealthCheckValve();
//    //tomcat.addEngineValves(accessLogValve, healthCheckValve);
//    tomcat.addEngineValves(healthCheckValve);
//    
//    RemoteIpValve remoteIpValve = new RemoteIpValve();
//    //remoteIpValve.setInternalProxies("localhost");
//    tomcat.addEngineValves(remoteIpValve);
//    
//    Collection<Valve> valves = tomcat.getEngineValves();
//    valves.forEach(item -> {
//      log.info("tomcat valve: {}", item.getClass());
//    });
//    return tomcat;
//  }
  
  // [TODO] ForwardedHeaderFilter 는 request 객체를 
  // wrapper 객체로 새로 생성하기 때문에
  // proxy 된 localhost ip 가 나오지 않음
  // 개발 시 iphone 에서 pc 로 요청할 때 ip 를 사용하는데
  // google oauth2 로그인 처리 시 localhost 가 아니면 400 오류가 발생함
  // 개발 기간 동안에는 아래 filter 를 주석 처리함
  @Bean
  FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
    final FilterRegistrationBean<ForwardedHeaderFilter> filterRegistrationBean = new FilterRegistrationBean<ForwardedHeaderFilter>();
    filterRegistrationBean.setFilter(new ForwardedHeaderFilter());
    filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return filterRegistrationBean;
  }

//  @Bean
//  public HttpSessionListener httpSessionListener() {
//    return new HttpSessionListener() {
//      @Override
//      public void sessionCreated(HttpSessionEvent se) {
//        System.out.println("Session Created with session id+" + se.getSession().getId());
//      }
//      @Override
//      public void sessionDestroyed(HttpSessionEvent se) {
//        System.out.println("Session Destroyed, Session id:" + se.getSession().getId());
//      }
//    };
//  }
  
}
