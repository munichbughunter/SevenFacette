const sevenfacette = require('../build/js/packages/SevenFacette-core').de.p7s1.qa.sevenfacette.kafka;

async function testCall() {
  const producer = sevenfacette.createProducer();
  console.log("producer call is working");
}
testCall();
