package de.p7s1.qa.sevenfacette.kafka;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class JKafkaConsumerTest {

  @Test
  void consumeMessages() {
    String topic = "ucp.sg-test-INGEST";
    KConsumer kafkaConsumer = new KConsumer(topic, 1000, "*", 5);

    kafkaConsumer.consume();

    System.out.println("Beginne mit einer Action wie DB Zeug anlegen... ");
    System.out.println("Hier bin ich mit dem anlegen fertig... ");

    kafkaConsumer.waitForMessage(5000);


    assertTrue(kafkaConsumer.hasMessage());
    assertEquals(500, kafkaConsumer.getMessageCount());

    System.out.println(kafkaConsumer.getMessageList().size());
  }
}
