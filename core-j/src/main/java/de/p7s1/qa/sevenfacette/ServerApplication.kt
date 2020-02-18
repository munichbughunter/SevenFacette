package de.p7s1.qa.sevenfacette

import de.p7s1.qa.sevenfacette.veritas.all
import de.p7s1.qa.sevenfacette.veritas.verification.contains
import de.p7s1.qa.sevenfacette.veritas.verification.containsExactly
import de.p7s1.qa.sevenfacette.veritas.verification.hasLength
import de.p7s1.qa.sevenfacette.veritas.verification.hasSize
import de.p7s1.qa.sevenfacette.veritas.verification.isBoolean
import de.p7s1.qa.sevenfacette.veritas.verification.isEqualTo
import de.p7s1.qa.sevenfacette.veritas.verification.isEqualToIgnoringGivenProperties
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
import org.apache.log4j.lf5.Log4JLogRecord
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import kotlin.reflect.KProperty1

@SpringBootApplication
class ServerApplication

fun main(args: Array<String>) {
    val logger = LoggerFactory.getLogger(ServerApplication::class.java)


    println(commonSharedCode(getPlatform()))
    val statement = DbStatements()
    statement.add("Hallo")
    statement.add("Flo")

    println(statement.statements)

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
}
