package market.lib.initial;

import org.springframework.boot.BootstrapRegistry;

public class BootstrapRegistryInitializer implements org.springframework.boot.BootstrapRegistryInitializer {

  @Override
  public void initialize(BootstrapRegistry registry) {
    System.setProperty("java.net.preferIPv4Stack", "true");
    System.setProperty("jasypt.encryptor.password", "123");
    System.setProperty("spring.datasource.password", "ENC(7YV8N1vTVDa8jwW2oDqZOA==)");
  }
}
