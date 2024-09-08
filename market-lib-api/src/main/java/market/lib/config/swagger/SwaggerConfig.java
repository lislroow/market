package market.lib.config.swagger;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Conditional(SwaggerProperties.class)
@Configuration
public class SwaggerConfig {

  //@Autowired
  //private SwaggerProperties properties;
}
