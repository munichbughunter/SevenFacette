package de.p7s1.qa.sevenfacette.db

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS
import de.p7s1.qa.sevenfacette.config.types.DatabaseConfig
import de.p7s1.qa.sevenfacette.utils.Logger
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer


/**
 * JVM specific implementation of the Database execution handler
 *
 * @constructor [dbConfig]
 *
 * @author Patrick Döring
 */
class Database(
        private val dbConfig: DatabaseConfig
) {
    private var logger = Logger()
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
                val entryCounter = AtomicInteger(1)

                dbStatements.list.forEach(Consumer {
                    if (it.toString().toLowerCase().startsWith(select)) {
                        conn.createStatement().use { sqlStatement ->
                            val resultSet = sqlStatement.executeQuery(it)
                            if (dbConfig.autoCommit && !conn.autoCommit) {
                                conn.commit()
                            }
                            result = convertResultSetToList(resultSet)
                        }
                    } else {
                        conn.createStatement().use { sqlStatement ->
                            sqlStatement.execute(it)
                            if (dbConfig.autoCommit && !conn.autoCommit) {
                                conn.commit()
                            }
                        }
                    }
                })
            }
        } catch (ex: SQLException) {
            logger.error("Error on opening database connection")
            logger.error(ex)
            throw RuntimeException(ex)
        }
        return result
    }

    /**
     * Executes a prepared statement and returns a JSONArray
     *
     * @param [preparedDbStatement] statement to execute
     *
     * @return [JSONArray]
     */
    fun executeSqlStatement(preparedDbStatement: SqlStatement) : JSONArray? {
        return execute(preparedDbStatement)
    }

    /**
     * Executes the sql statement and returns a JSONArray
     *
     * @param [preparedDbStatement] statement to execute
     *
     * @return [JSONArray]
     */
    private fun execute(preparedDbStatement: SqlStatement) : JSONArray? {
        var json :JSONArray? = JSONArray()
        if (!preparedDbStatement.validatePreparedStatement()) {
            throw RuntimeException("This is not a prepared statement: ${preparedDbStatement.sqlStatement}")
        }
        try {
            openConnection().use { conn ->
                if (preparedDbStatement.sqlStatement.toLowerCase().startsWith(select)) {
                    val resultSet = conn.prepareStatement(preparedDbStatement.sqlStatement).executeQuery()
                    json = convertResultSetToJson(resultSet)
                    if (dbConfig.autoCommit && !conn.autoCommit) {
                        conn.commit()
                    }
                } else {
                    conn.prepareStatement(preparedDbStatement.sqlStatement).execute()
                    if (dbConfig.autoCommit && !conn.autoCommit) {
                        conn.commit()
                    }
                }
            }
        } catch (ex: SQLException) {
            logger.error("Error on opening database connection")
            logger.error(ex)
            throw RuntimeException(ex)
        }
        return json
    }

    /**
     * Executes a prepared statement and returns a mapped List<T>
     *
     * @param [preparedDbStatement] statement to execute
     * @param [clazz] Entity class to map
     *
     * @return [T] Type
     */
    fun <T> executeSqlStatement(preparedDbStatement: SqlStatement, clazz: Class<T>) : List<T> {
        val result = execute(preparedDbStatement)
        val objectMapper = ObjectMapper()
        objectMapper.configure(FAIL_ON_EMPTY_BEANS, false)

        return objectMapper.readValue(result?.toJSONString(),
                objectMapper
                        .typeFactory
                        .constructParametricType(MutableList::class.java, Class.forName(clazz.name)))
    }

    /**
     * Convert result set to a JSONArray
     *
     * @param [resultSet] result set of the executed statement
     *
     * @return [JSONArray]
     */
    private fun convertResultSetToJson(resultSet: ResultSet?): JSONArray? {
        if (resultSet == null) return null
        val json = JSONArray()
        try {
            val metadata = resultSet.metaData
            val numColumns = metadata.columnCount

            while (resultSet.next())
            {
                val jsonObj = JSONObject()
                for (i in 1..numColumns)
                {
                    val columnName = metadata.getColumnName(i)
                    jsonObj[columnName] = resultSet.getObject(columnName)
                }
                json.add(jsonObj)
            }
        } catch (ex: SQLException) {
            throw RuntimeException("Error on converting result set to JSON", ex)
        }
        return json
    }

    /**
     * Convert result set to a list
     *
     * @param [rs] result set of the executed statement
     *
     * @return [result]
     */
    @Deprecated(message = "This function will be deleted in version 2.0.0")
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
            logger.error("Error on converting result set to List")
            logger.error(ex)
            throw RuntimeException(ex)
        }
    }
}
