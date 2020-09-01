package de.p7s1.qa.sevenfacette.kafka

import kotlin.js.Promise
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

@ExperimentalJsExport
@JsExport
fun createConsumer(): dynamic {
    val options: ConsumerOptions = js("({})")
    options.groupID = "test-group"
    return Kafka.consumer(options)
}

suspend fun makeProducer(): dynamic {
    //val client = HttpClient()
    return Kafka.producer().asDynamic()

}

@ExperimentalJsExport
@JsExport
fun createProducer(): Promise<dynamic> =
        GlobalScope.promise {
            makeProducer()
        }
    //return Kafka.producer()
//}
