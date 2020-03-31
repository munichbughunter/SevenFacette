package de.p7s1.qa.sevenfacette.kafka;

import static org.junit.jupiter.api.Assertions.assertTrue;

import de.p7s1.qa.sevenfacette.kafka.config.KConfig;
import de.p7s1.qa.sevenfacette.kafka.config.KTopicConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JKafkaConsumerTest {

  static KConfig kafkaConfig;
  static KTopicConfiguration ingestConsumerConfig;

  //private static KConsumerConfig ingestConsumerConfig;
  //private static KConsumerConfig persistConsumerConfig;
  //private static KConsumerConfig replicationConsumerConfig;
  //private static KConsumerConfig commitStreamConsumerConfig;

  private static KConsumer ingestConsumer;
  private static KConsumer persistConsumer;
  private static KConsumer replicationConsumer;
  private static KConsumer commitstreamConsumer;

  @BeforeAll
  static void setUp() {
    kafkaConfig = new KConfig();
    kafkaConfig.setAutoOffset(System.getenv("AUTO_OFFSET"));
    kafkaConfig.setBootstrapServer(System.getenv("BOOT_STRAP_SERVER"));
    kafkaConfig.setSaslMechanism(System.getenv("SASL_MECHANISM"));
    kafkaConfig.setKafkaUser(System.getenv("KAFKA_SASL_USERNAME"));
    kafkaConfig.setKafkaPW(System.getenv("KAFKA_SASL_PASSWORD"));
    kafkaConfig.setKafkaProtocol(System.getenv("KAFKA_PROTOCOL"));
    kafkaConfig.setSaslConfig(Boolean.parseBoolean(System.getenv("USE_SASL_CONFIG")));
    kafkaConfig.setMaxConsumingTime(5L);

    ingestConsumerConfig = new KTopicConfiguration(kafkaConfig);
    ingestConsumerConfig.setKafkaTopic(System.getenv("INGEST_TOPIC"));

  }

  @Test
  void consumerFactory() {
    KConsumer ingestConsumer = KFactory.createKConsumer(ingestConsumerConfig, true);

    System.out.println("Hier mache ich mein DB Zeugs....");
    System.out.println("FERTIG MIT DB ZEUG....");

    assertTrue(ingestConsumer.waitForMessage(5000));
    System.out.println(ingestConsumer.getMessageCount());
    System.out.println(ingestConsumer.getLastMessage());

/*
    NewConsumer newConsumer2 = ConFactory.getConsumer(ingestConsumerConfig, false);
    newConsumer2.consume();
    System.out.println("Hier mache ich mein DB Zeugs....");
    System.out.println("FERTIG MIT DB ZEUG....");

    System.out.println("Hier mache ich mein DB Zeugs....");
    System.out.println("FERTIG MIT DB ZEUG....");



    NewProducer newProducer1 = ConFactory.getProducer(ingestConsumerConfig, true, "test");
    newProducer1.send("test1");

    NewProducer newProducer2 = ConFactory.getProducer(ingestConsumerConfig, true, null);
    newProducer2.send("test2");

    NewProducer newProducer3 = ConFactory.getProducer(ingestConsumerConfig, false, null);
    newProducer2.send("test3");

 */
  }

  @Test
  void consumerConfig() {
    System.out.println("Hier mache ich mein DB Zeugs....");
    System.out.println("FERTIG MIT DB ZEUG....");

    System.out.println("KAFKA INGEST CONSUMER....");
    assertTrue(ingestConsumer.waitForMessage(5000));
    System.out.println(ingestConsumer.getMessageCount());
    System.out.println(ingestConsumer.getLastMessage());

    System.out.println("KAFKA PERSIST CONSUMER....");
    assertTrue(persistConsumer.waitForMessage(5000));
    System.out.println(persistConsumer.getMessageCount());
    System.out.println(persistConsumer.getLastMessage());

    System.out.println("KAFKA REPLICATION CONSUMER....");
    assertTrue(replicationConsumer.waitForMessage(5000));
    System.out.println(replicationConsumer.getMessageCount());
    System.out.println(replicationConsumer.getLastMessage());

    System.out.println("KAFKA COMMITSTREAM CONSUMER....");
    assertTrue(commitstreamConsumer.waitForMessage(5000));
    System.out.println(commitstreamConsumer.getMessageCount());
    System.out.println(commitstreamConsumer.getLastMessage());
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

@Test
  void consumerThought() {
    KConfig ingestConsumerConfig = new KConfig();
    ingestConsumerConfig.setAutoOffset(System.getenv("AUTO_OFFSET"));
    ingestConsumerConfig.setBootstrapServer(System.getenv("BOOT_STRAP_SERVER"));
    ingestConsumerConfig.setSaslMechanism(System.getenv("SASL_MECHANISM"));
    ingestConsumerConfig.setKafkaTopic(System.getenv("INGEST_TOPIC"));
    ingestConsumerConfig.setKafkaUser(System.getenv("KAFKA_SASL_USERNAME"));
    ingestConsumerConfig.setKafkaPW(System.getenv("KAFKA_SASL_PASSWORD"));
    ingestConsumerConfig.setKafkaProtocol(System.getenv("KAFKA_PROTOCOL"));
    ingestConsumerConfig.setSaslConfig(Boolean.parseBoolean(System.getenv("USE_SASL_CONFIG")));
    ingestConsumerConfig.setMaxConsumingTime(5L);

    KProvider generator = new KConsumerFactory();
    KConsumer ingestConsumer = generator.createConsumer(ingestConsumerConfig);
    ingestConsumer.consume();
  }

  @Test
  void factoryThought() {
    MonumentHandler greekMonumentHandler = new MonumentHandler(new GreekMonumentFactory(), "ATHENA", "PERICLES");
    greekMonumentHandler.IssueMessages();

    MonumentHandler egyptMonumentHandler = new MonumentHandler(new EgyptianMonumentFactory(), "SEKHMET", "HATCHEPSUT");
    egyptMonumentHandler.IssueMessages();
  }
   */
}
