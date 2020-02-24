package de.p7s1.qa.sevenfacette

import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ServerApplication

fun main(args: Array<String>) {
    println(commonSharedCode(getPlatform()))
    val statement = DbStatements()
    statement.add("Hallo")
    statement.add("Flo")

    println(statement.statements)
}
