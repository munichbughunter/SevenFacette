package de.p7s1.qa.sevenfacette.db

import de.p7s1.qa.sevenfacette.config.types.DatabaseConfig
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer
//import mu.KotlinLogging

/**
 * JVM specific implementation of the Database execution handler
 *
 * @constructor [dbConfig]
 *
 * @author Patrick Döring
 */
//private val logger = KotlinLogging.logger {}
class Database(
        private val dbConfig: DatabaseConfig
) {
    private val select = "select"
    private val emptyValue = "NULL"

    /**
     * Create connection for the specified jdbc driver, url and credentials
     *
     * @parameter [dbConfig]
     *
     * @return [Connection]
     */
    private fun openConnection(): Connection {
        return try {
            Class.forName(dbConfig.driver)
            DriverManager.getConnection(dbConfig.url, dbConfig.user, dbConfig.password)
        } catch (e: ClassNotFoundException) {
            throw RuntimeException(e)
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }

    /**
     * Executes database statements
     *
     * @parameter [dbStatements] statements to execute
     *
     * @return [result] as List<Map<String, Any>> or an empty list
     */
    @Deprecated(message = "This function will be deleted in version 2.0.0")
    fun executeStatements(dbStatements: DbStatements) : List<Map<String, Any>>? {
        var result: List<Map<String, Any>> = mutableListOf()
        try {
            openConnection().use { conn ->
  //              logger.info("Iterating over SQL-Statements")
                val entryCounter = AtomicInteger(1)

                dbStatements.list.forEach(Consumer {
                    if (it.toLowerCase().startsWith(select)) {
                        conn.createStatement().use {sqlStatement ->
                            val resultSet = sqlStatement.executeQuery(it)
                            if (dbConfig.autoCommit && !conn.autoCommit) {
                                conn.commit()
                            }
                            result = convertResultSetToList(resultSet)
                        }
                    } else {
                        conn.createStatement().use {sqlStatement ->
                            sqlStatement.execute(it)
                            if (dbConfig.autoCommit && !conn.autoCommit) {
                                conn.commit()
                            }
                        }
                    }
                })
            }
        } catch (ex: SQLException) {
    //        logger.error("Error on opening database connection. ", ex)
            throw RuntimeException(ex)
        }
        return result
    }

    // ToDo: Think about the function name!
    fun executePrepStatements(preparedDbStatements: DbStatements) : List<Map<String, Any>>? {
        var result: List<Map<String, Any>> = mutableListOf()
        try {
            openConnection().use { conn ->
                //              logger.info("Iterating over SQL-Statements")
                val entryCounter = AtomicInteger(1)

                preparedDbStatements.list.forEach(Consumer {
                    if (it.toLowerCase().startsWith(select)) {
                        conn.prepareStatement(it).use { preparedStatement ->
                            val resultSet = preparedStatement.executeQuery()
                        }
                        conn.createStatement().use {sqlStatement ->
                            val resultSet = sqlStatement.executeQuery(it)
                            if (dbConfig.autoCommit && !conn.autoCommit) {
                                conn.commit()
                            }
                            result = convertResultSetToList(resultSet)
                        }
                    } else {
                        conn.prepareStatement(it).use {sqlStatement ->
                            sqlStatement.execute(it)
                            if (dbConfig.autoCommit && !conn.autoCommit) {
                                conn.commit()
                            }
                        }
                    }
                })
            }
        } catch (ex: SQLException) {
            //        logger.error("Error on opening database connection. ", ex)
            throw RuntimeException(ex)
        }
        return result
    }
    /**
     * Convert result set to a list
     *
     * @param [rs] result set of the executed statement
     *
     * @return [result]
     */
    private fun convertResultSetToList(rs: ResultSet) : List<Map<String, Any>> {
        return try {
            val result: MutableList<Map<String, Any>> = mutableListOf()
            val md = rs.metaData
            val columns = md.columnCount
            while (rs.next()) {
                val row: MutableMap<String, Any> = HashMap(columns)
                for (i in 1..columns) {
                    if (rs.getObject(i) == null) {
                        row[md.getColumnName(i)] = emptyValue
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
