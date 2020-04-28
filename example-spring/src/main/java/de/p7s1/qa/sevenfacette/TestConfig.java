package de.p7s1.qa.sevenfacette;


import de.p7s1.qa.sevenfacette.config.types.HttpClientConfig;
import de.p7s1.qa.sevenfacette.kafka.config.KConfig;
import de.p7s1.qa.sevenfacette.kafka.config.KTableTopicConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
@Configuration
public class TestConfig {

  @Autowired
  GenericApplicationContext applicationContext;

  @Bean("httprestfulbooker")
  @ConfigurationProperties("sevenfacette.http.clients.restfulbooker")
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
  @ConfigurationProperties("sevenfacette.http.clients.testclient")
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

  @Bean("kafkaConfig")
  @ConfigurationProperties("sevenfacette.kafka")
  public KafkaConfig kafkaConfig() {
    return new KafkaConfig();
  }

  @Bean("ingestConsumer")
  public KTableTopicConfig ingestConsumer(@Qualifier("kafkaConfig") KafkaConfig kafkaConfig) {
    KConfig kConfig = new KConfig();
    kConfig.setBootstrapServer(kafkaConfig.getBootstrapserver());
    kConfig.setAutoOffset(kafkaConfig.getAutooffset());
    kConfig.setSaslMechanism(kafkaConfig.getSaslmechanism());
    kConfig.setKafkaUser(kafkaConfig.getSasluser());
    kConfig.setKafkaPW(kafkaConfig.getSaslpw());
    kConfig.setMaxConsumingTime(kafkaConfig.getConsumingtimeout());
    kConfig.setUseSASL(kafkaConfig.isSasl());
    kConfig.setKafkaProtocol(kafkaConfig.getSaslprotocol());

    KTableTopicConfig tableTopicConfig = new KTableTopicConfig(kConfig);
    tableTopicConfig.setKafkaTopic(kafkaConfig.getConsumer().get("ingest"));
    return tableTopicConfig;
  }
}
