package market.lib.config.encrypt;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptConfig {
  
  @Value("${jasypt.encryptor.password}")
  private String PASSWORD_KEY;
  
  @Bean("jasyptStringEncryptor")
  public StringEncryptor stringEncryptor(){
      PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
      SimpleStringPBEConfig config = new SimpleStringPBEConfig();
      config.setPassword(PASSWORD_KEY == null ? System.getProperty("jasypt.encryptor.password") : PASSWORD_KEY);
      config.setPoolSize("1");
      config.setAlgorithm("PBEWithMD5AndDES");
      config.setStringOutputType("base64");
      config.setKeyObtentionIterations("1000");
      config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
      encryptor.setConfig(config);
      return encryptor;
  }
}
