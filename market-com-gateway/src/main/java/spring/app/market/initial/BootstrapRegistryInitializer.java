package spring.app.market.initial;

import org.springframework.boot.BootstrapRegistry;

public class BootstrapRegistryInitializer implements org.springframework.boot.BootstrapRegistryInitializer {

  @Override
  public void initialize(BootstrapRegistry registry) {
    System.setProperty("java.net.preferIPv4Stack", "true");
    System.out.println("java.net.preferIPv4Stack=true");
  }
}
