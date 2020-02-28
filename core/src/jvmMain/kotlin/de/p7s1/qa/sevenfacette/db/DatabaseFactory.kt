package de.p7s1.qa.sevenfacette.db

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.ResultSet
import java.sql.SQLException
import java.util.concurrent.atomic.*


class DatabaseFactory {

    fun init(driver: String, url: String, user: String, pw: String) : Database{
        return Database.connect(url, driver, user, pw)
    }

    fun runStatements(dbStatements: DbStatements, db: Database) : List<Map<String,Any>> {
        var result: List<Map<String, Any>> = mutableListOf()
        transaction(db) {
            val entryCounter = AtomicInteger(1)
            dbStatements.list.forEach {
                if (it.toLowerCase().startsWith("select")) {
                    TransactionManager.current().exec(it) { rs ->
                        result = convertResultSetToList(rs)
                    }
                } else {
                    TransactionManager.current().exec(it)
                }
            }
        }
        return result
    }

    private fun convertResultSetToList(rs: ResultSet): List<Map<String, Any>> {
        return try {
            val result: MutableList<Map<String, Any>> = mutableListOf()
            val md = rs.metaData
            val columns = md.columnCount
            while (rs.next()) {
                val row: MutableMap<String, Any> = HashMap(columns)
                for (i in 1..columns) {
                    if (rs.getObject(i) == null) {
                        row[md.getColumnName(i)] = "NULL"
                    } else {
                        row[md.getColumnName(i)] = rs.getObject(i)
                    }
                }
                result.add(row)
            }
            result
        } catch (ex: SQLException) {
            throw RuntimeException("Error on converting result set to List", ex)
        }
    }
}
