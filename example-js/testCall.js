const sevenfacette = require('../build/js/packages/SevenFacette-core').de.p7s1.qa.sevenfacette.kafka;

async function testCall() {

  var kConfig = new sevenfacette.config.KConfig();
  kConfig.autoOffset = 'earliest';
  kConfig.bootstrapServer = 'localhost:9092';
  kConfig.saslMechanism = 'scram-sha-256';
  kConfig.kafkaUser = 'testuser';
  kConfig.kafkaPW = 'pw';

  var producerConfig = new sevenfacette.config.KTableTopicConfig(kConfig);
  console.log(producerConfig);
  producerConfig.kafkaTopic = 'testTopic';
  console.log(producerConfig.kafkaTopic);
  console.log(producerConfig.kafkaConfig.bootstrapServer);

  const kProducer = producerConfig.createKProducer();
  console.log(kProducer);
  console.log("producer call is working");

  const kConsumer = producerConfig.createKConsumer();
  console.log(kConsumer);
  console.log("consumer call is working");
  //kProducer.send("message to send");


}
testCall();
