const sevenfacetteConsumer = require('../build/js/packages/SevenFacette-core').de.p7s1.qa.sevenfacette.kafka;

async function testCall() {
  console.log(sevenfacetteConsumer.ConsumerSayHello());
}

testCall();
