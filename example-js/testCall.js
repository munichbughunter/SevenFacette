const sevenfacette = require('../build/js/packages/SevenFacette-core').de.p7s1.qa.sevenfacette.kafka;

async function testCall() {

  var kConfig = new sevenfacette.config.KConfig();
  kConfig.autoOffset = 'earliest';
  kConfig.bootstrapServer = 'localhost:9092';
  kConfig.useSASL = false;
  kConfig.maxConsumingTime = 5000;

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


  // Create KConsumer
  const kConsumer = producerConfig.createKConsumer();
  console.log(kConsumer);
  await kConsumer.connect();
  await kConsumer.subscribe({topic: producerConfig.kafkaTopic, fromBeginning: true});
  await kConsumer.run({
    eachMessage: async ({ topic, partition, message }) => {
      if (message.value.toString().includes('New')) {
        console.log("ready with consuming... We can stop now...");
        expectedMessage = message.value.toString();
        //kConsumer.disconnect();
        kConsumer.stop();
      }
      console.log({
        value: message.value.toString(),
      })
    },
  });

  await console.log(expectedMessage);
  await kConsumer.disconnect();

  //process.exit();

  await console.log("Jetzt können wir auch den Process killen...");
}
testCall();

