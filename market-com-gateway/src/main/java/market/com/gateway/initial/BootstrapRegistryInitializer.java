package market.com.gateway.initial;

import org.springframework.boot.BootstrapRegistry;

public class BootstrapRegistryInitializer implements org.springframework.boot.BootstrapRegistryInitializer {

  @Override
  public void initialize(BootstrapRegistry registry) {
    System.setProperty("java.net.preferIPv4Stack", "true");
    System.out.println("java.net.preferIPv4Stack=true");
  }
}
