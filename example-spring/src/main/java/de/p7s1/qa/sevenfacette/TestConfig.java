package de.p7s1.qa.sevenfacette;


import de.p7s1.qa.sevenfacette.config.types.HttpClientConfig;
import de.p7s1.qa.sevenfacette.config.types.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;


@Configuration
public class TestConfig {

  @Autowired
  GenericApplicationContext applicationContext;

  @Bean("httprestfulbooker")
  @ConfigurationProperties("spring.sevenfacette.http.clients.restfulbooker")
  public HttpTestClientConfig httpTestConfig() {
    return new HttpTestClientConfig();
  }

  @Bean("restfulbookerclient")
  public HttpClientConfig restfulBooker(@Qualifier("httprestfulbooker") HttpTestClientConfig config) {
    return new HttpClientConfig(config.getConnectionTimeout(), config.getConnectionRequestTimeout(),
                                config.getSocketTimeout(), config.getUrl(), null, null);

    // config.getHttpProxy(),
    // config.getAuthentication()
  }

  @Bean("httptestclient")
  @ConfigurationProperties("spring.sevenfacette.http.clients.testclient")
  public HttpTestClientConfig testClientConfig() {
    return new HttpTestClientConfig();
  }

  @Bean("testclient")
  public HttpClientConfig testClient(@Qualifier("httptestclient") HttpTestClientConfig testClientConfig) {
    return new HttpClientConfig(testClientConfig.getConnectionTimeout(), testClientConfig.getConnectionRequestTimeout(),
      testClientConfig.getSocketTimeout(), testClientConfig.getUrl(), null, null);

    //testClientConfig.getHttpProxy(),
    //  testClientConfig.getAuthentication()
  }

  @Bean("seleniumConfig")
  @ConfigurationProperties("spring.sevenfacette.web")
  public SeleniumConfig seleniumConfig() { return new SeleniumConfig(); }

  @Bean("browserConfiguration")
  public WebConfig browserConfig(@Qualifier("seleniumConfig") SeleniumConfig seleniumConfig) {
    return new WebConfig(seleniumConfig.isAutoClose(), seleniumConfig.getBaseUrl(), seleniumConfig.getBrowserName(),
      seleniumConfig.getCapabilities(), seleniumConfig.getChromeArgs(), seleniumConfig.getChromeBin(),
      seleniumConfig.isHighlightBorder(), seleniumConfig.getHighlightColor(), seleniumConfig.getHighlightSize(),
      seleniumConfig.getHighlightStyle(), seleniumConfig.getListenerClass(), seleniumConfig.getPollingInterval(),
      seleniumConfig.getRemoteUrl(), seleniumConfig.getReportDir(), seleniumConfig.getScreenSize(),
      seleniumConfig.isStartMaximized(), seleniumConfig.getTimeout());
  }

  @Bean("kafkaConfig")
  @ConfigurationProperties("sevenfacette.kafka")
  public KafkaConfig kafkaConfig() {
    return new KafkaConfig();
  }

}
