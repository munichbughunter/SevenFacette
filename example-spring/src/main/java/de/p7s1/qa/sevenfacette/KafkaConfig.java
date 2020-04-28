package de.p7s1.qa.sevenfacette;

import java.util.HashMap;
import java.util.Map;


public class KafkaConfig {
  private String bootstrapserver;
  private String autooffset;
  private boolean sasl;
  private String saslmechanism;
  private String saslprotocol;
  private String sasluser;
  private String saslpw;
  private Map<String, String> topicMap = new HashMap<>();
  private Long consumingtimeout;

  public void setConsumerIngest(String consumerIngest) {
    this.topicMap.put("ingest", consumerIngest);
  }

  public void setConsumerReplication(String consumerReplication) {
    this.topicMap.put("replication", consumerReplication);
  }

  public Long getConsumingtimeout() {
    return consumingtimeout;
  }

  public void setConsumingtimeout(Long consumingtimeout) {
    this.consumingtimeout = consumingtimeout;
  }

  public String getBootstrapserver() {
    return bootstrapserver;
  }

  public void setBootstrapserver(String bootstrapserver) {
    this.bootstrapserver = bootstrapserver;
  }

  public String getAutooffset() {
    return autooffset;
  }

  public void setAutooffset(String autooffset) {
    this.autooffset = autooffset;
  }

  public String getSaslmechanism() {
    return saslmechanism;
  }

  public void setSaslmechanism(String saslmechanism) {
    this.saslmechanism = saslmechanism;
  }

  public String getSasluser() {
    return sasluser;
  }

  public void setSasluser(String sasluser) {
    this.sasluser = sasluser;
  }

  public String getSaslpw() {
    return saslpw;
  }

  public void setSaslpw(String saslpw) {
    this.saslpw = saslpw;
  }

  public boolean isSasl() {
    return sasl;
  }

  public void setSasl(boolean sasl) {
    this.sasl = sasl;
  }

  public String getSaslprotocol() {
    return saslprotocol;
  }

  public void setSaslprotocol(String saslprotocol) {
    this.saslprotocol = saslprotocol;
  }

  public Map<String, String> getConsumer() {
    return topicMap;
  }
}
