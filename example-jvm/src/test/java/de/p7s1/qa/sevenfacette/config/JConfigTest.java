package de.p7s1.qa.sevenfacette.config;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class JConfigTest {

  @Test
  void loadConfig() throws IOException {
    Configurator configurator = new ConfigBuilder()
      .addSource(YMLPropertySource.from("build/resources/test/test.yml"))
      .build();

    configurator.configure(SomeClass.class);


    System.out.println(configurator.getConfigValue("Warning"));
  }
}
