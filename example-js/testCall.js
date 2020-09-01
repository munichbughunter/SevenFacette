const sevenfacetteK = require('../build/js/packages/SevenFacette-core').de.p7s1.qa.sevenfacette.kafka;
'use strict';


async function testCall() {
  //console.log(sevenfacetteConsumer.ConsumerSayHello());

  // const producer = kafka.producer()
  const producer = sevenfacetteK.createProducer();
  //sevenfacetteConsumer.createConsumer();
  console.log("Test");
  //console.log(consumer)
  //console.log(sevenfacetteConsumer.createConsumer());
  //var consumer = sevenfacetteConsumer.createConsumer();
  //console.log(consumer['groupId']);

}

testCall();
