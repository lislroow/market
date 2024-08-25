package spring.app.market.config.webmvc;

import java.io.IOException;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;

/*
@Slf4j
@EnableConfigurationProperties({WebMvcProperties.class})
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  @Bean
  FilterRegistrationBean<Filter> encodingFilter() {
    FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
    bean.setFilter(new EncodingFilter());
    bean.setOrder(1);
    bean.addUrlPatterns("/*");
    return bean;
  }
}
*/
public class EncodingFilter implements Filter {

  final String ENCODING = "utf-8";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    request.setCharacterEncoding(ENCODING);
    chain.doFilter(request, response);
  }
}