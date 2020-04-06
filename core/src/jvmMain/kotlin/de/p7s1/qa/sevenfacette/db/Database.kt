package de.p7s1.qa.sevenfacette.db

import de.p7s1.qa.sevenfacette.db.config.DConfig
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
class Database(
        private val dbConfig: DConfig
) {
    private val select = "select"
    private val emptyValue = "NULL"
    /**
     * create connection for the specified jdbc url and credentials
     */
    fun openConnection(): Connection {
        return try {
            Class.forName(dbConfig.dbDriver)
            DriverManager.getConnection(dbConfig.dbUrl, dbConfig.dbUser, dbConfig.dbPW)
        } catch (e: ClassNotFoundException) {
            throw RuntimeException(e)
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }

    fun executeStatements(dbStatements: DbStatements) : List<Map<String, Any>>? {
        var result: List<Map<String, Any>> = mutableListOf()
        try {
            openConnection().use { conn ->
                //val executor = Executor(conn, dbConfig.autoCommit, true)
                //logger.info("Iterating over SQL-Statements")
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
            //logger.error("Error on opening database connection. ", ex)
            throw RuntimeException(ex)
        }

        return result
    }

    /**
     * Convert result set to a list
     *
     * @param [rs] result set of the executed statement
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
