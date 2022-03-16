package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.screenplay.Ability
import kotlinx.coroutines.ObsoleteCoroutinesApi
import java.time.Duration
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
class ConsumerAbility (private val consumer: KConsumer) : Ability {

    override fun name(): String {
        return abilityName
    }

    companion object {
        var abilityName : String = ""
       /* fun withConfiguration(name: String, autoStart: Boolean) : ConsumerAbility {
            abilityName = name
            return ConsumerAbility(KFactory.createKConsumer(name, autoStart))
        }*/
    }

    fun filterByValue(pattern: String, pollingTime: Int): List<KRecord> {
        return consumer.filterByValue(pattern, pollingTime)
    }

    fun filterByKey(pattern: String, pollingTime: Int): List<KRecord> {
        return consumer.filterByKey(pattern, pollingTime)
    }

    fun waitForKRecordsCount(count: Int, pollingTime: Int): MutableList<KRecord>? {
        return consumer.waitForRecords(count, pollingTime)
    }

    fun consume() {
        consumer.consume()
    }

    fun getKRecords(): MutableList<KRecord>? {
        return consumer.getRecords()
    }

    fun getKRecordsCount() : Int {
        return consumer.recordsCount
    }

    fun getLastKRecord(): KRecord? {
        return consumer.lastRecord
    }

    fun stopConsumer() {
        consumer.stop()
    }
}
