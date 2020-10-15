const sfKafka = require('../build/js/packages/SevenFacette-core').de.p7s1.qa.sevenfacette.kafka;
const sfConfig = require('../build/js/packages/SevenFacette-core').de.p7s1.qa.sevenfacette.config;

async function testConsumer() {

  var kConfig = new sfConfig.types.KafkaTopicConfig();
  kConfig.autoOffset = true;
  kConfig.bootstrapServer = 'localhost:9092';
  kConfig.maxConsumingTime = 50;
  kConfig.kafkaTopic = 'test';

  console.log(kConfig);

  // Now we will check if we can consume from a topic
  // Create KConsumer
  const sfConsumer = new sfKafka.KFactory().createKConsumer("testConsumer", kConfig);
  console.log(sfConsumer);

  let consumedMessages = [];

  await sfConsumer.connect();
  await sfConsumer.subscribe({topic: kConfig.kafkaTopic, fromBeginning: kConfig.autoOffset})
  await sfConsumer.run({
    eachMessage: async ({ topic, partition, message }) => {
      console.log({
        value: message.value.toString(),
      })
      consumedMessages.push(message.value.toString())
    },
  });
  setTimeout(() => {
    sfConsumer.stop();
    sfConsumer.disconnect();
    console.log(consumedMessages);
  }, 8000);



  //var kConfig = new sevenfacette.config.KConfig();
  //kConfig.autoOffset = 'earliest';
  //kConfig.bootstrapServer = 'localhost:9092';
  //kConfig.useSASL = false;
  //kConfig.maxConsumingTime = 5000;

  /*
  var producerConfig = new sevenfacette.config.KTableTopicConfig(kConfig);
  producerConfig.kafkaTopic = 'test';
  console.log(producerConfig);
  console.log(producerConfig.kafkaTopic);
  console.log(producerConfig.kafkaConfig.bootstrapServer);

  // Create KProducer
  const kProducer = producerConfig.createKProducer();
  console.log(kProducer);

  // Connect to the Kafka and send message
  await kProducer.connect();
  await kProducer.send({
    topic: producerConfig.kafkaTopic,
    messages: [
      { key: 'message1', value: 'New Test message from JS'}
    ]
  });

  console.log("producer call is working");
  await kProducer.disconnect();
  var expectedMessage = "";




  //process.exit();

  await console.log("Jetzt können wir auch den Process killen...");

   */
}
testConsumer();

