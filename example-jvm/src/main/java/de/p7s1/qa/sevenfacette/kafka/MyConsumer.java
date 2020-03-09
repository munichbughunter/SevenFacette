package de.p7s1.qa.sevenfacette.kafka;

import kotlinx.coroutines.GlobalScope;


public class MyConsumer extends KConsumer {
  private String topic = "MyCoolKafkaTopic";

  public MyConsumer(String topic, int expectedMessageCount,
    String pattern, int latchWaitTime) {
    super(topic, expectedMessageCount, pattern, latchWaitTime);
  }
}
