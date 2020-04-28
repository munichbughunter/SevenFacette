package de.p7s1.qa.sevenfacette;

import de.p7s1.qa.sevenfacette.kafka.config.KConfig;

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
public class AConfig {

  private String name;
  private String baseUrl;

  public String getBootstrapServer() {
    return bootstrapServer;
  }

  public void setBootstrapServer(String bootstrapServer) {
    this.bootstrapServer = bootstrapServer;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  private String bootstrapServer;
  private String topic;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }
}
