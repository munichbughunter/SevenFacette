const sfKafka = require('../build/js/packages/SevenFacette-core').de.p7s1.qa.sevenfacette.kafka;
const sfConfig = require('../build/js/packages/SevenFacette-core').de.p7s1.qa.sevenfacette.config;

// ES6
//import {createConsumer} from "../build/js/packages/SevenFacette-core').de.p7s1.qa.sevenfacette.kafka";
//import consi from "../build/js/packages/SevenFacette-core').de.p7s1.qa.sevenfacette.kafka";

async function testConsumer() {

  var kConfig = new sfConfig.types.KafkaTopicConfig();
  kConfig.autoOffset = true;
  kConfig.bootstrapServer = 'localhost:9092';
  kConfig.maxConsumingTime = 50;
  kConfig.kafkaTopic = 'test';

  console.log(kConfig);

  // Now we will check if we can consume from a topic
  // Create KConsumer
  //const sfConsumer = new sfKafka.KFactory().createKConsumer("testConsumer", kConfig);
  // console.log(sfConsumer);
  //
  // let consumedMessages = [];
  //
  // await sfConsumer.connect();
  // await sfConsumer.subscribe({topic: kConfig.kafkaTopic, fromBeginning: kConfig.autoOffset})
  // await sfConsumer.run({
  //   eachMessage: async ({ topic, partition, message }) => {
  //     console.log({
  //       value: message.value.toString(),
  //     })
  //     consumedMessages.push(message.value.toString())
  //   },
  // });
  //
  // setTimeout(() => {
  //   sfConsumer.stop();
  //   sfConsumer.disconnect();
  //   console.log(consumedMessages);
  // }, 8000);



  // Now we will check if we can produce a message to a topic
  // Create KProducer

  //const kenjiConsumer = createConsumer(kConfig);
  //consi.createKConsumer(kConfig);

  var producer = new sfKafka.KProducer("testProducer", kConfig).createKProducer();
  console.log("PRODUCER:");
  console.log(producer);
  producer.sendKafkaMessage();

  console.log(producer.getTopic());

  //var sfProducer = new sfKafka.KProducer("testProducer", kConfig).createKProducer();

  //console.log(sfProducer);

  //sfProducer.sendKafkaMessage();
  //console.log(sfProducer.sendMessage());
  // console.log(sfProducer);
  //
  // Connect to the Kafka and send message
  //await producer.connect();
  // await producer.send({
  //   topic: kConfig.kafkaTopic,
  //   messages: [
  //     { key: 'message1', value: 'New Test message from JS Producer'}
  //   ]
  // });

  setTimeout(() => {
    console.log("producer call is working");
    producer.disconnect();
  }, 5000);
}
testConsumer();

