package de.p7s1.qa.sevenfacette.config;

public class KConsumerConfig {
  private String topic;
  private String bootstrapserver;
  private String auto_offset;
  private String sasl_mechanism;
  private String kafkaUser;
  private String kafkaPw;
  private Long maxConsumingTime;

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public String getBootstrapserver() {
    return bootstrapserver;
  }

  public void setBootstrapserver(String bootstrapserver) {
    this.bootstrapserver = bootstrapserver;
  }

  public String getAuto_offset() {
    return auto_offset;
  }

  public void setAuto_offset(String auto_offset) {
    this.auto_offset = auto_offset;
  }

  public String getSasl_mechanism() {
    return sasl_mechanism;
  }

  public void setSasl_mechanism(String sasl_mechanism) {
    this.sasl_mechanism = sasl_mechanism;
  }

  public String getKafkaUser() {
    return kafkaUser;
  }

  public String getKafkaPw() {
    return kafkaPw;
  }

  public Long getMaxConsumingTime() {
    return maxConsumingTime;
  }

  public void setMaxConsumingTime(Long maxConsumingTime) {
    this.maxConsumingTime = maxConsumingTime;
  }

  public void setUp() {
    this.kafkaUser = System.getenv("KAFKA_SASL_USERNAME");
    this.kafkaPw = System.getenv("KAFKA_SASL_PASSWORD");
  }
}
