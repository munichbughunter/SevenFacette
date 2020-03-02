package de.p7s1.qa.sevenfacette

import de.p7s1.qa.sevenfacette.veritas.all
import de.p7s1.qa.sevenfacette.veritas.verifyThat
import de.p7s1.qa.sevenfacette.veritas.verification.hasLength
import de.p7s1.qa.sevenfacette.veritas.verification.startsWith
import mu.KotlinLogging


fun main() {
    val logger = KotlinLogging.logger {  }

    logger.info { "Hello World!" }

    val statementForExecution = de.p7s1.qa.sevenfacette.db.DbStatements()
    statementForExecution.add("statement")
    println(statementForExecution.statements)

    verifyThat("Test").all {
        startsWith("T")
        hasLength(4)
    }
}
