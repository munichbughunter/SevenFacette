package de.p7s1.qa.sevenfacette.kafka;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class JKafkaConsumerTest {

  @Test
  void consumeMessages() {
    String ingestTopic = "Topic 1";
    String commitStreamTopic = "Topic 2";
    String forwardReplicationTopic = "Topic 3";
    String persistenceTopic = "Topic 4";

    KConsumer kafkaIngestConsumer = new KConsumer(ingestTopic, 1000, "*", 5);
    KConsumer kafkaCommitStreamConsumer = new KConsumer(commitStreamTopic, 1000, "*", 5);
    KConsumer kafkaReplicationConsumer = new KConsumer(forwardReplicationTopic, 1000, "*", 5);
    KConsumer kafkaPersistConsumer = new KConsumer(persistenceTopic, 1000, "*", 5);

    kafkaCommitStreamConsumer.consume();
    kafkaReplicationConsumer.consume();
    kafkaPersistConsumer.consume();
    kafkaIngestConsumer.consume();

    System.out.println("Beginne mit einer Action wie DB Zeug anlegen... ");
    System.out.println("Hier bin ich mit dem anlegen fertig... ");

    System.out.println("AUSGABE COMMITSTREAM CONSUMER");
    assertTrue(kafkaCommitStreamConsumer.waitForMessage(5000));
    System.out.println(kafkaCommitStreamConsumer.getMessageCount());
    //assertEquals(500, kafkaCommitStreamConsumer.getMessageCount());

    System.out.println("AUSGABE REPLICATION CONSUMER");
    assertTrue(kafkaReplicationConsumer.waitForMessage(5000));
    System.out.println(kafkaReplicationConsumer.getMessageCount());
    //assertEquals(500, kafkaReplicationConsumer.getMessageCount());

    System.out.println("AUSGABE PERSIST CONSUMER");
    assertTrue(kafkaPersistConsumer.waitForMessage(5000));
    System.out.println(kafkaPersistConsumer.getMessageCount());
    //assertEquals(500, kafkaPersistConsumer.getMessageCount());

    System.out.println("AUSGABE INGEST CONSUMER");
    assertTrue(kafkaIngestConsumer.waitForMessage(5000));
    System.out.println(kafkaIngestConsumer.getMessageCount());
    //assertEquals(500, kafkaIngestConsumer.getMessageCount());
  }
}
