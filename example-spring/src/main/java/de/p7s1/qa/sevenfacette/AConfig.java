package de.p7s1.qa.sevenfacette;




public class AConfig {

  private String name;

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
}
