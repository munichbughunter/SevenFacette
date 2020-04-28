package de.p7s1.qa.sevenfacette;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableConfigurationProperties
public class Demo {
  public static void main(String[] args) {
    SpringApplication.run(Demo.class, args);
  }

  @Bean
  public Runner runner() {
    return new Runner();
  }
}
