package de.p7s1.qa.sevenfacette.db

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer

class Database
/**
 * Constructs an sevenfacette Database object.
 *
 * @param driver  db driver
 * @param url  db url
 * @param user  db user
 * @param password  db password
 */(private val driver: String, private val url: String, private val user: String, private val password: String) {

    @JvmOverloads
    fun executeStatements(dbStatements: DbStatements, autoCommit: Boolean = false) {
        try {
            openConnection().use { conn ->
                val executor = Executor(conn, autoCommit, true)
                val entryCounter = AtomicInteger(1)
                dbStatements.list.forEach(Consumer { entry: String ->
                    executor.execCommand(
                            entryCounter.getAndIncrement(),
                            entry
                    )
                })
            }
        } catch (ex: SQLException) {
            throw RuntimeException(ex)
        }
    }

    fun checkDbEntry(dbStatements: DbStatements): List<Map<String, Any>> {
        if(dbStatements.size() != 1) {
            throw RuntimeException("Number of SELECT statements has to be 1")
        }

        try {
            openConnection().use { conn ->
                val executor = Executor(conn, autoCommit = false, stopOnError = true)
                return executor.execSelectStatement(dbStatements[0])
            }
        } catch (ex: SQLException) {
            throw RuntimeException(ex)
        }
    }

    private fun openConnection(): Connection {
        return try {
            Class.forName(driver)
            DriverManager.getConnection(url, user, password)
        } catch (e: ClassNotFoundException) {
            throw RuntimeException(e)
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }
}
