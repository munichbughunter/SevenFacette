package de.p7s1.qa.sevenfacette

import de.p7s1.qa.sevenfacette.db.DatabaseFactory
import de.p7s1.qa.sevenfacette.db.ScriptReader
import de.p7s1.qa.sevenfacette.kafka.KConsumer
import de.p7s1.qa.sevenfacette.kafka.KProducer
import de.p7s1.qa.sevenfacette.veritas.all
import de.p7s1.qa.sevenfacette.veritas.verification.contains
import de.p7s1.qa.sevenfacette.veritas.verification.hasLength
import de.p7s1.qa.sevenfacette.veritas.verification.hasSize
import de.p7s1.qa.sevenfacette.veritas.verification.isBoolean
import de.p7s1.qa.sevenfacette.veritas.verification.isEqualTo
import de.p7s1.qa.sevenfacette.veritas.verification.isInt
import de.p7s1.qa.sevenfacette.veritas.verification.isLessThan
import de.p7s1.qa.sevenfacette.veritas.verification.isNullLiteral
import de.p7s1.qa.sevenfacette.veritas.verification.isObject
import de.p7s1.qa.sevenfacette.veritas.verification.isString
import de.p7s1.qa.sevenfacette.veritas.verification.isTrue
import de.p7s1.qa.sevenfacette.veritas.verification.isValueNodeArray
import de.p7s1.qa.sevenfacette.veritas.verification.jsonIsEqualTo
import de.p7s1.qa.sevenfacette.veritas.verification.jsonNodeOf
import de.p7s1.qa.sevenfacette.veritas.verification.jsonPath
import de.p7s1.qa.sevenfacette.veritas.verification.startsWith
import de.p7s1.qa.sevenfacette.veritas.verifyThat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.util.*

@SpringBootApplication
class ServerApplication

fun main(args: Array<String>) {
    val logger = LoggerFactory.getLogger(ServerApplication::class.java)

    // Reading SQL-Scripts from Resources
    val content = ScriptReader().getStatements("/database/cc/test.sql")

    // Start Database handling

    val postGresDB = DatabaseFactory().init("org.postgresql.Driver","jdbc:postgresql://localhost:5432/testdb", "user", "pw")

    val informixDB = DatabaseFactory().init("com.informix.jdbc.IfxDriver","db", "user", "pw")

    //val resultPVV = DatabaseFactory().runStatements(statements, informixDB)
    // End Database Handling

    // Start Assertion Handling
    verifyThat("Test").all {
        startsWith("T")
        hasLength(4)
    }

    val objectJson = """
            {
              "foo": "aaa",
              "bar": 1,
              "baz": true,
              "qux": null,
              "quux": [ "bbb", "ccc" ],
              "corge": {
                "grault": "ddd",
                "garply": "eee",
                "waldo": "fff"
              }
            }
        """



    val expectedJson = """
            {
              "foo": "aaa",
              "bar": 1,
              "baz": true,
              "qux": null,
              "corge": {
                "grault": "ddd",
                "garply": "eee",
                "waldo": "fff"
              }
            }
        """

    verifyThat(objectJson).jsonIsEqualTo(expectedJson)

    verifyThat(jsonNodeOf(objectJson)).isObject().all {
        jsonPath("$.foo").isString().isEqualTo("aaa")
        // or jsonPath("$.foo").isEqualTo("aaa")
        jsonPath("$.bar").isInt().isLessThan(10)
        jsonPath("$.baz").isBoolean().isTrue()
        jsonPath("$.qux").isNullLiteral()
        jsonPath("$.quux").isValueNodeArray().all {
            hasSize(2)
        }
        jsonPath("$.corge").isObject().all {
            jsonPath("$.grault").isString().contains("DD", ignoreCase = true)
            jsonPath("$.garply").isString().isEqualTo("eee")
        }
        jsonPath("$.corge.waldo").isString().isEqualTo("fff")
    }

    // End Assertion Handling

    // Start Kafka Handling Producer
    val topic = "MyCoolKafkaTopic"
    val kafkaProducer = KProducer(topic)
    // Get Producer topic
    println(kafkaProducer.getTopic())

    // Start Kafka Handling Consumer
    val kafkaConsumer = KConsumer(topic, 10, "*", 30)
    GlobalScope.async {
        kafkaConsumer.consume()
    }
    //kafkaConsumer.consume()
    println(kafkaConsumer.getMessageCount())
    println(kafkaConsumer.getAllMessages())
    // End Kafka Handling
}
