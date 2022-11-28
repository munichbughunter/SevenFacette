package de.p7s1.qa.sevenfacette.db

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS
import de.p7s1.qa.sevenfacette.config.types.DatabaseConfig
import de.p7s1.qa.sevenfacette.utils.Logger
import org.awaitility.Awaitility.with
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import java.sql.*
import java.time.Duration
import java.util.concurrent.TimeUnit.SECONDS
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
    private val update = "update"
    private val insert = "insert"
    private val emptyValue = "NULL"
    private lateinit var conn: Connection

    fun getConn(): Connection {
        return this.conn
    }
    /**
     * Create connection for the specified jdbc driver, url and credentials
     *
     * @parameter [dbConfig]
     *
     * @return [Connection]
     */
    private fun initConnection(): Connection {
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
     * Open database connection
     *
     * @return [Connection]
     */
    fun openConnection(): Connection {
        conn = initConnection()
        conn.autoCommit = dbConfig.autoCommit
        return conn
    }

    /**
     * Close database connection
     *
     * @return [Connection]
     */
    fun closeConnection() {
        conn.close()
    }

    /**
     * Validates the sql statement
     *
     * @parameter [preparedDbStatement]
     * @return [Boolean]
     */
    private fun validateStatement(preparedDbStatement: SqlStatement): Boolean {
        if (!preparedDbStatement.validatePreparedStatement()) {
            throw RuntimeException("This is not a prepared statement: ${preparedDbStatement.sqlStatement}")
        }
        return true
    }

    /**
     * Safe commit
     *
     */
    private fun safeCommit() {
        if (!dbConfig.autoCommit && !conn.autoCommit) {
            this.conn.commit()
        }
    }

    /**
     * Executes a prepared statement and returns a JSONArray
     *
     * @param [preparedDbStatement] statement to execute
     *
     * @return [JSONArray]
     */
    fun executeSqlStatement(preparedDbStatement: SqlStatement, conn: Connection? = null, autoClose: Boolean? = false) : JSONArray? {
        validateStatement(preparedDbStatement)
        if (conn == null || conn.isClosed) {
            openConnection()
        }
        return execute(preparedDbStatement, this.conn, autoClose!!)
    }

    /**
     * Executes the sql s tatement and returns a JSONArray
     *
     * @param [preparedDbStatement] statement to execute
     *
     * @return [JSONArray]
     */
    private fun execute(preparedDbStatement: SqlStatement, conn: Connection, autoClose: Boolean): JSONArray? {
        var json: JSONArray? = JSONArray()
        try {
            if (preparedDbStatement.sqlStatement.toLowerCase().startsWith(select)) {
                val resultSet = this.conn.prepareStatement(preparedDbStatement.sqlStatement).executeQuery()
                json = convertResultSetToJson(resultSet)
                this.safeCommit()
            } else if (preparedDbStatement.sqlStatement.toLowerCase().startsWith(update)) {
                val stmt = this.conn.prepareStatement(preparedDbStatement.sqlStatement, Statement.RETURN_GENERATED_KEYS)
                val updatedRows = stmt.executeUpdate()
                if (updatedRows == 0) {
                    logger.error("NO ROWS UPDATED - PLEASE CHECK YOUR STATEMENT: ${preparedDbStatement.sqlStatement}")
                    throw SQLException("SQL Update failed!")
                }
                this.safeCommit()
            } else if (preparedDbStatement.sqlStatement.toLowerCase().startsWith(insert)){
                val stmt = this.conn.prepareStatement(preparedDbStatement.sqlStatement, Statement.RETURN_GENERATED_KEYS)
                stmt.executeUpdate()

                this.safeCommit()
                json = extractInsertedValues(stmt)
            } else {
                this.conn.prepareStatement(preparedDbStatement.sqlStatement).execute()
                this.safeCommit()
            }

            if (autoClose) {
                closeConnection()
            }
        } catch (sqlEx: SQLException) {
            logger.error("Error on executing database statement")
            logger.error(sqlEx)
            throw RuntimeException(sqlEx)
        }
        return json
    }

    private fun extractInsertedValues(stmt: PreparedStatement): JSONArray? {
        var json: JSONArray? = JSONArray()
        var tableName = ""
        var columnLabel = ""
        stmt.generatedKeys.use { generatedKey ->
            if (generatedKey.next()) {
                val numColumns = generatedKey.metaData.columnCount
                for (i in 1..numColumns) {

                    columnLabel = generatedKey.metaData.getColumnLabel(i)
                    tableName = generatedKey.metaData.getTableName(i)
                    logger.debug("Table name: $tableName")
                    logger.debug("Table $tableName is autoincrement: ${generatedKey.metaData.isAutoIncrement(numColumns)}")
                    logger.debug("Column label: $columnLabel")
                }
                json = execute(SqlStatement("select * from $tableName where $columnLabel = ${generatedKey.getObject(columnLabel)}"), this.conn, false)
            }
        }
        return json
    }

    /**
     * Executes a prepared statement until time duration is reached and returns a JSONArray.
     *
     * @param [preparedDbStatement] statement to execute
     *
     * @return [JSONArray]
     */
    fun waitUntilExistsOrUpdated(preparedDbStatement: SqlStatement, conn: Connection? = null, autoClose: Boolean? = false, timeout: Duration = Duration.ofSeconds(5), pollInterval: Duration = Duration.ofSeconds(1)) : JSONArray? {
        with().pollInterval(pollInterval.seconds, SECONDS).await().atMost(timeout.seconds, SECONDS).until {
            executeSqlStatement(preparedDbStatement, conn, autoClose)?.size!! > 0
        }
        return executeSqlStatement(preparedDbStatement, conn, autoClose)
    }

    /**
     * Executes a prepared statement until time duration is reached and returns a JSONArray.
     *
     * @param [preparedDbStatement] statement to execute
     *
     * @return [JSONArray]
     */
    fun waitUntilDeleted(preparedDbStatement: SqlStatement, conn: Connection? = null, autoClose: Boolean? = false, timeout: Duration = Duration.ofSeconds(5), pollInterval: Duration = Duration.ofSeconds(1)) : JSONArray? {
        with().pollInterval(pollInterval.seconds, SECONDS).await().atMost(timeout.seconds, SECONDS).until {
            executeSqlStatement(preparedDbStatement, conn, autoClose)?.size!! == 0
        }
        return executeSqlStatement(preparedDbStatement, conn, autoClose)
    }

    /**
     * Executes a prepared statement until time duration is reached and returns a mapped List<T>.
     *
     * @param [preparedDbStatement] statement to execute
     * @param [clazz] Entity class to map
     *
     * @return [T] Type
     */
    fun <T> waitUntilExistsOrUpdated(preparedDbStatement: SqlStatement, clazz: Class<T>, conn: Connection? = null, autoClose: Boolean? = false, timeout: Duration = Duration.ofSeconds(5), pollInterval: Duration = Duration.ofSeconds(1)) : List<T> {
        with().pollInterval(pollInterval.seconds, SECONDS).await().atMost(timeout.seconds, SECONDS).until {
            executeSqlStatement(preparedDbStatement, clazz, conn, autoClose).isNotEmpty()
        }
        return executeSqlStatement(preparedDbStatement, clazz, conn, autoClose)
    }

    /**
     * Executes a prepared statement and returns a mapped List<T>
     *
     * @param [preparedDbStatement] statement to execute
     * @param [clazz] Entity class to map
     *
     * @return [T] Type
     */
    fun <T> executeSqlStatement(preparedDbStatement: SqlStatement, clazz: Class<T>, conn: Connection? = null, autoClose: Boolean? = false) : List<T> {
        if (conn == null) {
            openConnection()
        }
        val result = execute(preparedDbStatement, this.conn, autoClose!!)
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
                    // val boolAutoIncrement = metadata.isAutoIncrement(i)
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
    private fun convertResultSetToList(rs: ResultSet) : List<Map<String, Any?>> {
        return try {
            val result: MutableList<Map<String, Any?>> = mutableListOf()
            val md = rs.metaData
            val columns = md.columnCount
            while (rs.next()) {
                val row: MutableMap<String, Any?> = HashMap(columns)
                for (i in 1..columns) {
                    row[md.getColumnName(i)] = rs.getObject(i)
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

    /**
     * Executes database statements
     *
     * @parameter [dbStatements] statements to execute
     *
     * @return [result] as List<Map<String, Any>> or an empty list
     */
    @Deprecated(message = "This function will be deleted in version 2.0.0")
    fun executeStatements(dbStatements: DbStatements) : List<Map<String, Any?>>? {
        var result: List<Map<String, Any?>> = mutableListOf()
        try {
            initConnection().use { conn ->
                dbStatements.list.forEach(Consumer {
                    if (it.toString().toLowerCase().startsWith(select)) {
                        conn.createStatement().use { sqlStatement ->
                            val resultSet = sqlStatement.executeQuery(it)
                            this.safeCommit()
                            result = convertResultSetToList(resultSet)
                        }
                    } else {
                        conn.createStatement().use { sqlStatement ->
                            sqlStatement.execute(it)
                            this.safeCommit()
                        }
                    }
                })
                conn.close()
            }
        } catch (ex: SQLException) {
            logger.error("Error on opening database connection")
            logger.error(ex)
            throw RuntimeException(ex)
        }
        return result
    }
}
