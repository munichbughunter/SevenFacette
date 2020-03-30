package de.p7s1.qa.sevenfacette.kafka;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JKafkaConsumerTest {

  @BeforeAll
  static void setUp() {
  }

  @Test
  void consumerConfig() {
    KConfig ingestConsumerConfig = new KConfig();
    ingestConsumerConfig.setAutoOffset(System.getenv("AUTO_OFFSET"));
    ingestConsumerConfig.setBootstrapServer(System.getenv("BOOT_STRAP_SERVER"));
    ingestConsumerConfig.setSaslMechanism(System.getenv("SASL_MECHANISM"));
    ingestConsumerConfig.setKafkaTopic(System.getenv("INGEST_TOPIC"));
    ingestConsumerConfig.setMaxConsumingTime(5L);
    ingestConsumerConfig.setUp();

    KConfig persistConsumerConfig = new KConfig();
    persistConsumerConfig.setAutoOffset(System.getenv("AUTO_OFFSET"));
    persistConsumerConfig.setBootstrapServer(System.getenv("BOOT_STRAP_SERVER"));
    persistConsumerConfig.setSaslMechanism(System.getenv("SASL_MECHANISM"));
    persistConsumerConfig.setKafkaTopic(System.getenv("PERSISTENCE_TOPIC"));
    persistConsumerConfig.setMaxConsumingTime(5L);
    persistConsumerConfig.setUp();

    KConfig replicationConsumerConfig = new KConfig();
    replicationConsumerConfig.setAutoOffset(System.getenv("AUTO_OFFSET"));
    replicationConsumerConfig.setBootstrapServer(System.getenv("BOOT_STRAP_SERVER"));
    replicationConsumerConfig.setSaslMechanism(System.getenv("SASL_MECHANISM"));
    replicationConsumerConfig.setKafkaTopic(System.getenv("FORWARD_REPLICATION_TOPIC"));
    replicationConsumerConfig.setMaxConsumingTime(5L);
    replicationConsumerConfig.setUp();

    KConfig commitstreamConsumerConfig = new KConfig();
    commitstreamConsumerConfig.setAutoOffset(System.getenv("AUTO_OFFSET"));
    commitstreamConsumerConfig.setBootstrapServer(System.getenv("BOOT_STRAP_SERVER"));
    commitstreamConsumerConfig.setSaslMechanism(System.getenv("SASL_MECHANISM"));
    commitstreamConsumerConfig.setKafkaTopic(System.getenv("COMMIT_STREAM_TOPIC"));
    commitstreamConsumerConfig.setMaxConsumingTime(5L);
    commitstreamConsumerConfig.setUp();


    KConsumer kafkaIngestConsumer = new KConsumer(ingestConsumerConfig);
    KConsumer kafkaPersistConsumer = new KConsumer(persistConsumerConfig);
    KConsumer kafkaReplicationConsumer = new KConsumer(ingestConsumerConfig);
    KConsumer kafkaCommitStreamConsumer = new KConsumer(commitstreamConsumerConfig);


    kafkaIngestConsumer.consume();
    kafkaPersistConsumer.consume();
    kafkaReplicationConsumer.consume();
    kafkaCommitStreamConsumer.consume();


    System.out.println("Hier mache ich mein DB Zeugs....");
    System.out.println("FERTIG MIT DB ZEUG....");

    System.out.println("KAFKA INGEST CONSUMER....");
    assertTrue(kafkaIngestConsumer.waitForMessage(5000));
    System.out.println(kafkaIngestConsumer.getMessageCount());
    System.out.println(kafkaIngestConsumer.getLastMessage());

    System.out.println("KAFKA PERSIST CONSUMER....");
    assertTrue(kafkaPersistConsumer.waitForMessage(5000));
    System.out.println(kafkaPersistConsumer.getMessageCount());
    System.out.println(kafkaPersistConsumer.getLastMessage());

    System.out.println("KAFKA REPLICATION CONSUMER....");
    assertTrue(kafkaReplicationConsumer.waitForMessage(5000));
    System.out.println(kafkaReplicationConsumer.getMessageCount());
    System.out.println(kafkaReplicationConsumer.getLastMessage());

    System.out.println("KAFKA COMMITSTREAM CONSUMER....");
    assertTrue(kafkaCommitStreamConsumer.waitForMessage(5000));
    System.out.println(kafkaCommitStreamConsumer.getMessageCount());
    System.out.println(kafkaCommitStreamConsumer.getLastMessage());
  }



  /*
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
    assertTrue(kafkaCommitStreamConsumer.waitForMessage(5000), "Commitstream Consumer has message consumed");
    System.out.println(kafkaCommitStreamConsumer.getMessageCount());
    System.out.println(kafkaCommitStreamConsumer.getLastMessage());

    System.out.println("AUSGABE REPLICATION CONSUMER");
    assertTrue(kafkaReplicationConsumer.waitForMessage(5000));
    System.out.println(kafkaReplicationConsumer.getMessageCount());
    System.out.println(kafkaReplicationConsumer.getLastMessage());

    System.out.println("AUSGABE PERSIST CONSUMER");
    assertTrue(kafkaPersistConsumer.waitForMessage(5000));
    System.out.println(kafkaPersistConsumer.getMessageCount());
    System.out.println(kafkaPersistConsumer.getLastMessage());

    System.out.println("AUSGABE INGEST CONSUMER");
    assertTrue(kafkaIngestConsumer.waitForMessage(5000));
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
    //KConsumer kafkaIngestConsumer = new KConsumer(ingestTopic);

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
    assertTrue(kafkaCommitStreamConsumer.waitForMessage(5000),
      "Commitstream Consumer has message consumed");
    System.out.println(kafkaCommitStreamConsumer.getMessageCount());
    System.out.println(kafkaCommitStreamConsumer.getLastMessage());

    System.out.println("AUSGABE REPLICATION CONSUMER");
    assertTrue(kafkaReplicationConsumer.waitForMessage(5000));
    System.out.println(kafkaReplicationConsumer.getMessageCount());
    System.out.println(kafkaReplicationConsumer.getLastMessage());

    kafkaCommitStreamConsumer.getMessageList();
//    kafkaCommitStreamConsumer.filterMessages(kafkaCommitStreamConsumer.getMessageList().stream().filter(), "Streamindex...");



    kafkaPersistConsumer.reConfigure(1, "", 5, "<MyStreamIndex>");
    kafkaIngestConsumer.reConfigure(1, "", 5, "<MyStreamIndex>");

    System.out.println("AUSGABE PERSIST CONSUMER");
    assertTrue(kafkaPersistConsumer.waitForMessage(5000));
    System.out.println(kafkaPersistConsumer.getMessageCount());
    System.out.println(kafkaPersistConsumer.getLastMessage());

    System.out.println("AUSGABE INGEST CONSUMER");
    assertTrue(kafkaIngestConsumer.waitForMessage(5000));
    System.out.println(kafkaIngestConsumer.getMessageCount());
    System.out.println(kafkaIngestConsumer.getLastMessage());
  }

   */
}
