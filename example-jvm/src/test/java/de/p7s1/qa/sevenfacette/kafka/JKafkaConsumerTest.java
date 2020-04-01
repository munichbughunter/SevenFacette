package de.p7s1.qa.sevenfacette.kafka;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class JKafkaConsumerTest {

  @Test
  void consumeMessages() {
    String commitStreamTopic = System.getenv("COMMIT_STREAM_TOPIC");
    String forwardReplicationTopic = System.getenv("FORWARD_REPLICATION_TOPIC");
    String persistenceTopic = System.getenv("PERSISTENCE_TOPIC");
    String ingestTopic = System.getenv("INGEST_TOPIC");

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
    //assertTrue(kafkaCommitStreamConsumer.waitForMessage(5000), "Commitstream Consumer has message consumed");
    System.out.println(kafkaCommitStreamConsumer.getMessageCount());
    System.out.println(kafkaCommitStreamConsumer.getLastMessage());

    System.out.println("AUSGABE REPLICATION CONSUMER");
    //assertTrue(kafkaReplicationConsumer.waitForMessage(5000));
    System.out.println(kafkaReplicationConsumer.getMessageCount());
    System.out.println(kafkaReplicationConsumer.getLastMessage());

    System.out.println("AUSGABE PERSIST CONSUMER");
    //assertTrue(kafkaPersistConsumer.waitForMessage(5000));
    System.out.println(kafkaPersistConsumer.getMessageCount());
    System.out.println(kafkaPersistConsumer.getLastMessage());

    System.out.println("AUSGABE INGEST CONSUMER");
    //assertTrue(kafkaIngestConsumer.waitForMessage(5000));
    System.out.println(kafkaIngestConsumer.getMessageCount());
    System.out.println(kafkaIngestConsumer.getLastMessage());
  }

  @Test
  void reconfigureConsumer() {
    String commitStreamTopic = System.getenv("COMMIT_STREAM_TOPIC");
    String forwardReplicationTopic = System.getenv("FORWARD_REPLICATION_TOPIC");
    String persistenceTopic = System.getenv("PERSISTENCE_TOPIC");
    String ingestTopic = System.getenv("INGEST_TOPIC");

    KConsumer kafkaIngestConsumer = new KConsumer(ingestTopic, 1000, "", 5);
    KConsumer kafkaCommitStreamConsumer = new KConsumer(commitStreamTopic, 1000, "*", 5);
    KConsumer kafkaReplicationConsumer = new KConsumer(forwardReplicationTopic, 1000, "*", 5);
    KConsumer kafkaPersistConsumer = new KConsumer(persistenceTopic, 1000, "", 5);

    kafkaCommitStreamConsumer.consume();
    kafkaReplicationConsumer.consume();
    kafkaPersistConsumer.consume();
    kafkaIngestConsumer.consume();

    System.out.println("Beginne mit einer Action wie DB Zeug anlegen... ");
    System.out.println("Hier bin ich mit dem anlegen fertig... ");

    System.out.println("AUSGABE COMMITSTREAM CONSUMER");
    //assertTrue(kafkaCommitStreamConsumer.waitForMessage(5000),     "Commitstream Consumer has message consumed");
    System.out.println(kafkaCommitStreamConsumer.getMessageCount());
    System.out.println(kafkaCommitStreamConsumer.getLastMessage());

    System.out.println("AUSGABE REPLICATION CONSUMER");
    //assertTrue(kafkaReplicationConsumer.waitForMessage(5000));
    System.out.println(kafkaReplicationConsumer.getMessageCount());
    System.out.println(kafkaReplicationConsumer.getLastMessage());

    //kafkaPersistConsumer.reConfigure(1, "", 5, "<MyStreamIndex>");
    //kafkaIngestConsumer.reConfigure(1, "", 5, "<MyStreamIndex>");

    System.out.println("AUSGABE PERSIST CONSUMER");
    //assertTrue(kafkaPersistConsumer.waitForMessage(5000));
    System.out.println(kafkaPersistConsumer.getMessageCount());
    System.out.println(kafkaPersistConsumer.getLastMessage());

    System.out.println("AUSGABE INGEST CONSUMER");
    //assertTrue(kafkaIngestConsumer.waitForMessage(5000));
    System.out.println(kafkaIngestConsumer.getMessageCount());
    System.out.println(kafkaIngestConsumer.getLastMessage());
  }
}
