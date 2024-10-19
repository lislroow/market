package market.lib.config.webmvc;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  
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
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/webjars/**")
            .addResourceLocations("/webjars/")
            .resourceChain(false);
    registry.setOrder(1);
  }
  
  
  // ForwardedHeaderFilter 는 request 객체를 
  // wrapper 객체로 새로 생성하기 때문에
  // proxy 된 localhost ip 가 나오지 않음
  // 개발 시 iphone 에서 pc 로 요청할 때 ip 를 사용하는데
  // google oauth2 로그인 처리 시 localhost 가 아니면 400 오류가 발생함
  // 개발 기간 동안에는 아래 filter 를 주석 처리함
  @Bean
  FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
    final FilterRegistrationBean<ForwardedHeaderFilter> filterRegistrationBean = new FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(new ForwardedHeaderFilter());
    filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return filterRegistrationBean;
  }
}
