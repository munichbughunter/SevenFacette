const sfKafka = require('../build/js/packages/SevenFacette-core').de.p7s1.qa.sevenfacette.kafka;
const sfConfig = require('../build/js/packages/SevenFacette-core').de.p7s1.qa.sevenfacette.config;
// ES6
//import {createConsumer} from "../build/js/packages/SevenFacette-core').de.p7s1.qa.sevenfacette.kafka";
//import consi from "../build/js/packages/SevenFacette-core').de.p7s1.qa.sevenfacette.kafka";

async function testConsumer() {



  var kConfig = new sfConfig.types.KafkaTopicConfig();
  kConfig.autoOffset = true;
  kConfig.bootstrapServer = 'localhost:9092';
  kConfig.maxConsumingTime = 5;
  kConfig.topicName = 'test';
  kConfig.autoOffset = 'earliest';
  kConfig.useSASLAuthentication = false;
  kConfig.saslMechanism = '';
  kConfig.saslUsername = '';
  kConfig.saslPassword = '';
  kConfig.groupID = '';
  kConfig.kafkaProtocol = '';

  var producer = new sfKafka.KProducer(kConfig).createKProducer();
  await producer.sendKeyMessage("Testmessage", "Here I am rocking like a hurricane");
  setTimeout(() => {
    producer.disconnect();
  }, 5000);
  //
  // //const consumer = createConsumer(kConfig);
  // //consi.createKConsumer(kConfig);
  // const kafka = new sfKafka.KConsumer(kConfig).createKConsumer();
  // const sfConsumer = kafka.getConsumer();
  //
  // await sfConsumer.run({
  //   eachMessage: async ({ topic, partition, message }) => {
  //     //const prefix = `${topic}[${partition} | ${message.offset}] / ${message.timestamp}`
  //     //console.log(`- ${prefix} ${message.key}#${message.value}`)
  //     kafka.addKRecord(message.key.toString(), message.value.toString(), message.offset, partition)
  //   },
  // })
  //
  // setTimeout(() => {
  //   kafka.shutdown();
  //
  //   console.log("Records Count:");
  //   console.log(kafka.getKRecordsCount());
  //
  //   console.log("Messages:");
  //   console.log(kafka.getMessages());
  //
  //   console.log("filterByValue");
  //   console.log(kafka.filterByValue('Here I am rocking like a hurricane'));
  //
  //   console.log("filterByKey");
  //   console.log(kafka.filterByKey('Hallo'));
  //
  //   console.log("All messages")
  //   console.log(kafka.getMessages());
  //
  //   console.log("Letzte Nachricht")
  //   console.log(kafka.getLastKRecord());
  //
  // }, kConfig.maxConsumingTime*1000);
}
testConsumer();

