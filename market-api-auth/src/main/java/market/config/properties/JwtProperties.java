package market.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = JwtProperties.PREFIX)
@Data
public class JwtProperties {

  public static final String PREFIX = "jwt";
  private Cert cert = new Cert();
  private Token token = new Token();
  
  @Data
  public class Cert {
    private String publicKeyFilePath;
    private String privateKeyFilePath;
  }
  
  @Data
  public class Token {
    private String issuer;
    private Long accessTokenExpireTime;
    private Long refreshTokenExpireTime;
  }
}
