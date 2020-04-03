package de.p7s1.qa.sevenfacette.kafka;

import static org.junit.jupiter.api.Assertions.assertTrue;

import de.p7s1.qa.sevenfacette.kafka.config.KConfig;
import de.p7s1.qa.sevenfacette.kafka.config.KTableTopicConfig;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;
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

    assertTrue(consumer.waitForKRecords(5000));
    System.out.println(consumer.getKRecordsCount());
    System.out.println(consumer.getLastKRecord());

    List<KRecord> recordList = consumer.getKRecords()
      .stream()
      .filter(kRecord -> Objects.requireNonNull(kRecord.getValue()).contains("42681550000000000"))
      .collect(Collectors.toList());


    recordList.forEach(record -> System.out.println(record.getKey() + "\n" + record.getValue() + "\n" + record.getOffset() + "\n" + record.getPartition()));
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
}
