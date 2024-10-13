package market.com.config;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.optionals.OptionalDecoder;

@Configuration
public class FeignConfig {

  @Bean
  Decoder feignDecoder() {
    return new OptionalDecoder(new ResponseEntityDecoder(new SpringDecoder(() -> new HttpMessageConverters())));
  }

  @Bean
  Encoder feignEncoder() {
    return new SpringEncoder(() -> new HttpMessageConverters());
  }
}