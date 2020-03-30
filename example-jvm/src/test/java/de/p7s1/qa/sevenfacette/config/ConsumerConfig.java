package de.p7s1.qa.sevenfacette.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumerConfig {
  @JsonProperty String COMMITSTREAM_TOPIC;
  @JsonProperty String REPLICATION_TOPIC;
  @JsonProperty String PERSIST_TOPIC;
  @JsonProperty String INGEST_TOPIC;
  @JsonProperty String BOOT_STRAP_SERVER;
  @JsonProperty String AUTO_OFFSET;
  @JsonProperty String SASL_MECHANISM;

  public String getCOMMITSTREAM_TOPIC() {
    return COMMITSTREAM_TOPIC;
  }

  public String getREPLICATION_TOPIC() {
    return REPLICATION_TOPIC;
  }

  public String getPERSIST_TOPIC() {
    return PERSIST_TOPIC;
  }

  public String getINGEST_TOPIC() {
    return INGEST_TOPIC;
  }

  public String getBOOT_STRAP_SERVER() {
    return BOOT_STRAP_SERVER;
  }

  public String getAUTO_OFFSET() {
    return AUTO_OFFSET;
  }

  public String getSASL_MECHANISM() {
    return SASL_MECHANISM;
  }

  public String getKafkaPw() {
    return kafkaPw;
  }

  public void setKafkaPw(String kafkaPw) {
    this.kafkaPw = kafkaPw;
  }

  public String getKafkaUser() {
    return kafkaUser;
  }

  public void setKafkaUser(String kafkaUser) {
    this.kafkaUser = kafkaUser;
  }

  private String kafkaPw;
  private String kafkaUser;

  public void setUp() {
    this.kafkaUser = System.getenv("KAFKA_SASL_USERNAME");
    this.kafkaPw = System.getenv("KAFKA_SASL_PASSWORD");
  }
}
