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

    override fun <T> withConfiguration(name: String): ConsumerAbility {
        abilityName = name
        return ConsumerAbility(KFactory.createKConsumer(name, true))
    }

    companion object {
        var abilityName : String = ""
       /* fun withConfiguration(name: String, autoStart: Boolean) : ConsumerAbility {
            abilityName = name
            return ConsumerAbility(KFactory.createKConsumer(name, autoStart))
        }*/
    }

    fun filterByValue(pattern: String, pollingTime: Duration): List<KRecord> {
        return consumer.filterByValue(pattern, pollingTime)
    }

    fun filterByKey(pattern: String, pollingTime: Duration): List<KRecord> {
        return consumer.filterByKey(pattern, pollingTime)
    }

    fun waitForKRecordsCount(count: Int, pollingTime: Duration): ConcurrentLinkedQueue<KRecord> {
        return consumer.waitForKRecordsCount(count, pollingTime)
    }

    fun waitForKRecords(waitingTime: Int): Boolean {
        return consumer.waitForKRecords(waitingTime)
    }

    @ObsoleteCoroutinesApi
    fun consume() {
        consumer.consume()
    }

    fun getKRecords(): ConcurrentLinkedQueue<KRecord> {
        return consumer.getKRecords()
    }

    fun getKRecordsCount() : Int {
        return consumer.getKRecordsCount()
    }

    fun getLastKRecord(): KRecord? {
        return consumer.getLastKRecord()
    }

    fun stopConsumer() {
        consumer.stopConsumer()
    }
}
