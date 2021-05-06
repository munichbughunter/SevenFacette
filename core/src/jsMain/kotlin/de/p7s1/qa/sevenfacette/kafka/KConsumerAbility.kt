package de.p7s1.qa.sevenfacette.kafka

import io.ktor.util.KtorExperimentalAPI

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
@KtorExperimentalAPI
@ExperimentalJsExport
@JsName("KConsumerAbility")
open class KConsumerAbility (private val kafkaConsumer: KConsumer) {

    private var abilities = mutableListOf<KafkaConsumerAbility>()
    private var consumerAbility = kafkaConsumer
        get() {
            return field
        }
        set(value) {
            field = value
        }

    @JsName("withConfiguration")
    fun withConfiguration(name: String, autoStart: Boolean) : Array<KafkaConsumerAbility> {
        consumerAbility = createKConsumer(name, autoStart)
        abilities.add(KafkaConsumerAbility(name, consumerAbility))
        return abilities.toTypedArray()
    }

    fun addKRecord(key: String, value: String, offset: Int, partition: Int) {
        kafkaConsumer.addKRecord(key, value, offset, partition)
    }

    fun getMessages() : Array<KRecord> {
        return kafkaConsumer.getMessages()
    }

    fun getKRecordsCount() : Int {
        return kafkaConsumer.getKRecordsCount()
    }

    fun filterByValue(pattern: String) : Array<KRecord> {
        return kafkaConsumer.filterByValue(pattern)
    }

    fun filterByKey(pattern: String) : Array<KRecord> {
        return kafkaConsumer.filterByKey(pattern)
    }

    fun getLastKRecord() : KRecord? {
        return kafkaConsumer.getLastKRecord()
    }

    fun shutdown() {
        kafkaConsumer.shutdown()
    }

    fun getTopic() : String {
        return kafkaConsumer.getTopic()
    }

    fun getConsumer(): dynamic {
        return kafkaConsumer.getConsumer()
    }
}
