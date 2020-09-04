const sevenfacette = require('../build/js/packages/SevenFacette-core').de.p7s1.qa.sevenfacette.kafka;

async function testCall() {

  var kConfig = new sevenfacette.config.KConfig();
  kConfig.autoOffset = 'earliest';
  kConfig.bootstrapServer = 'localhost:9092';
  console.log(kConfig.autoOffset);

  var producerConfig = new sevenfacette.config.KTableTopicConfig(kConfig);
  producerConfig.kafkaTopic = 'testTopic';
  console.log(producerConfig.kafkaTopic);
  console.log(producerConfig.kafkaConfig.bootstrapServer);

  const kProducer = producerConfig.createKProducer();
  console.log(kProducer);

  kProducer.send("message to send");

  console.log("producer call is working");
}
testCall();
