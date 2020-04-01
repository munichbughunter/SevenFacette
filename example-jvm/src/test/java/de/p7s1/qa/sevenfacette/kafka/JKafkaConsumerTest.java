package de.p7s1.qa.sevenfacette.kafka;

import static org.junit.jupiter.api.Assertions.assertTrue;

import de.p7s1.qa.sevenfacette.kafka.config.KConfig;
import de.p7s1.qa.sevenfacette.kafka.config.KTableTopicConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JKafkaConsumerTest {

  static KConfig kafkaConfig;
  static KTableTopicConfig ingestConsumerConfig;

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
    kafkaConfig.setMaxConsumingTime(5L);

    ingestConsumerConfig = new KTableTopicConfig(kafkaConfig);
    ingestConsumerConfig.setKafkaTopic(System.getenv("INGEST_TOPIC"));
  }

  @Test
  void consumerFactory() {
    KConsumer consumer = ingestConsumerConfig.createKConsumer(true);

    assertTrue(consumer.waitForMessage(5000));

    System.out.println(consumer.getMessageCount());
    System.out.println(consumer.getLastMessage());
    consumer.getMessages();

    KConsumer ingestConsumer = KFactory.createKConsumer(ingestConsumerConfig, true);

    System.out.println("Hier mache ich mein DB Zeugs....");
    System.out.println("FERTIG MIT DB ZEUG....");

    assertTrue(ingestConsumer.waitForMessage(5000));
    System.out.println(ingestConsumer.getMessageCount());
    System.out.println(ingestConsumer.getLastMessage());

  }

  @Test
  void producerFactory() {
    KProducer autoFlushProducer = ingestConsumerConfig.createKProducer(true);
    autoFlushProducer.send("Testmessage die automatisch geflushed wird");

    KProducer kFactor = KFactory.createKProducer(ingestConsumerConfig, true);
    kFactor.send("Testmessage die nicht automatisch geflushed wird");

    KProducer manuellProducer = ingestConsumerConfig.createKProducer(false);
    manuellProducer.send("Testmessage die nicht automatisch geflushed wird");
    manuellProducer.flush();
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
}
