package de.p7s1.qa.sevenfacette.db

import de.p7s1.qa.sevenfacette.db.exception.SqlErrorExecutingStatementException
import java.lang.RuntimeException
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

internal class Executor(
        private val connection: Connection,
        private val autoCommit: Boolean,
        private val stopOnError: Boolean
) {
    private val statement: Statement
        get() = connection.createStatement()
    /*
    Only for executing select statements
     */
    fun execSelectStatement(selectStatement: String): List<Map<String, Any>> {
        try {
            statement.use { sqlStatement ->
                val resultSet = sqlStatement.executeQuery(selectStatement)
                return convertResultSetToList(resultSet)
            }
        } catch (ex: SQLException) {
            throw SqlErrorExecutingStatementException("Error on executing statement", ex)
        }
    }

    fun execCommand(lineNumber: Int, currentStatement: String) {
        try {
            statement.use { sqlStatement ->
              sqlStatement.execute(currentStatement)
                if (autoCommit && !connection.autoCommit) {
                    connection.commit()
                }
            }
        } catch (ex: SQLException) {
            throw SqlErrorExecutingStatementException("Error on executing statement", ex)
        }
    }

    private fun convertResultSetToList(rs: ResultSet): List<Map<String, Any>> {
        return try {
            val md = rs.metaData
            val columns = md.columnCount
            val list: MutableList<Map<String,Any>> = mutableListOf()
            while (rs.next()) {
                val row: MutableMap<String, Any> = HashMap(columns)
                for ( i in 1..columns) {
                    row[md.getColumnName(i)] = rs.getObject(i)
                }
                list.add(row)
            }
            list
        } catch (ex: SQLException) {
            throw RuntimeException("...!", ex)
        }
    }
}
