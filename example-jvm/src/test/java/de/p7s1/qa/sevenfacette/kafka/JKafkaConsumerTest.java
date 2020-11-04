package de.p7s1.qa.sevenfacette.kafka;

import static org.junit.jupiter.api.Assertions.assertTrue;

import de.p7s1.qa.sevenfacette.kafka.config.KConfig;
import de.p7s1.qa.sevenfacette.kafka.config.KTableTopicConfig;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class JKafkaConsumerTest {

  static KConfig kafkaConfig;
  static KTableTopicConfig ingestConsumerConfig;
  static KTableTopicConfig commitConsumerConfig;
  static KTableTopicConfig replicationConsumerConfig;
  static KTableTopicConfig persistConsumerConfig;

  private static KConsumer ingestConsumer;
  private static KConsumer persistConsumer;
  private static KConsumer replicationConsumer;
  private static KConsumer commitstreamConsumer;

  //@BeforeAll
  static void setUp() {
    kafkaConfig = new KConfig();
    kafkaConfig.setAutoOffset("");
    kafkaConfig.setBootstrapServer("");
    kafkaConfig.setSaslMechanism("");
    kafkaConfig.setKafkaUser("");
    kafkaConfig.setKafkaPW(System.getenv(""));
    kafkaConfig.setKafkaProtocol("");
    kafkaConfig.setMaxConsumingTime(45L);

    ingestConsumerConfig = new KTableTopicConfig(kafkaConfig);
    ingestConsumerConfig.setKafkaTopic("");

    commitConsumerConfig = new KTableTopicConfig(kafkaConfig);
    commitConsumerConfig.setKafkaTopic("");

    replicationConsumerConfig = new KTableTopicConfig(kafkaConfig);
    replicationConsumerConfig.setKafkaTopic("");

    persistConsumerConfig = new KTableTopicConfig(kafkaConfig);
    persistConsumerConfig.setKafkaTopic("");
  }

  @Test
  @Disabled
  void consumerFactory() {
    ingestConsumer = ingestConsumerConfig.createKConsumer(true);
    commitstreamConsumer = commitConsumerConfig.createKConsumer(true);
    persistConsumer = persistConsumerConfig.createKConsumer(true);
    replicationConsumer = replicationConsumerConfig.createKConsumer(true);

//    assertTrue(consumer.waitForKRecords(5000));
//    System.out.println(consumer.getKRecordsCount());
//    System.out.println(consumer.getLastKRecord());
//
//    List<KRecord> recordList = consumer.getKRecords()
//      .stream()
//      .filter(kRecord -> Objects.requireNonNull(kRecord.getValue()).contains("42681550000000000"))
//      .collect(Collectors.toList());
    int count = 0;
    boolean keepGoing = true;
    while (keepGoing) {
      //commitstreamConsumer.getKRecordsCount();
      System.out.println("Message count CS: " + commitstreamConsumer.getKRecordsCount());
      //System.out.println("Messages CS: " + commitstreamConsumer.getKRecords());
      //replicationConsumer.getKRecordsCount();
      System.out.println("Message count RC: " + replicationConsumer.getKRecordsCount());
      //System.out.println("Messages RC: " + replicationConsumer.getKRecords());
      //persistConsumer.getKRecordsCount();
      System.out.println("Message count PC: " + persistConsumer.getKRecordsCount());
      //System.out.println("Messages PC: " + persistConsumer.getKRecords());
      //ingestConsumer.getKRecordsCount();
      System.out.println("Message count IC: " + ingestConsumer.getKRecordsCount());
      //System.out.println("Messages IC: " + ingestConsumer.getKRecords());

      if (count == 1000) {
        keepGoing = false;
      } else {
        count++;
      }

      commitstreamConsumer.waitForKRecords(2000);
      System.out.println(commitstreamConsumer.getKRecordsCount());
      System.out.println(commitstreamConsumer.getKRecords());

      replicationConsumer.waitForKRecords(2000);
      System.out.println(replicationConsumer.getKRecordsCount());
      System.out.println(replicationConsumer.getKRecords());

      persistConsumer.waitForKRecords(2000);
      System.out.println(persistConsumer.getKRecordsCount());
      System.out.println(persistConsumer.getKRecords());

      ingestConsumer.waitForKRecords(2000);
      System.out.println(ingestConsumer.getKRecordsCount());
      System.out.println(ingestConsumer.getKRecords());
    }
    //recordList.forEach(record -> System.out.println(record.getKey() + "\n" + record.getValue() + "\n" + record.getOffset() + "\n" + record.getPartition()));
  }

  @Test
  @Disabled
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
