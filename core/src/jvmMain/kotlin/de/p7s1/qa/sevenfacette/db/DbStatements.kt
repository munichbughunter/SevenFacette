package de.p7s1.qa.sevenfacette.db

import java.util.Collections

class DbStatements {
    private val statements: MutableList<DbStatement> = mutableListOf()
    private val statementsAsString: MutableList<String> = mutableListOf()

    fun add(dbStatement: DbStatement) {
        statements.add(dbStatement)
    }

    @Deprecated(message = "This function will be deleted in version 2.0.0. Use DbStatement instead.")
    fun add(statement: String) {
        statementsAsString.add(statement)
    }

    val list: List<DbStatement>
        get() = Collections.unmodifiableList(statements)

    @Deprecated(message = "This function will be deleted in version 2.0.0. Use get which returns List<DbStatement> instead.")
    val listStringStatements: List<String>
        get() = Collections.unmodifiableList(statementsAsString)

    operator fun contains(dbStatement: DbStatement) : Boolean {
        return dbStatement in statements
    }

    @Deprecated(message = "This function will be deleted in version 2.0.0. Use contains(dbStatement: DbStatement) instead.")
    operator fun contains(statement: String): Boolean {
        return statement in statementsAsString
    }

    operator fun get(index: Int) : DbStatement {
        return statements[index]
    }

    @Deprecated(message = "This function will be deleted in version 2.0.0. Use get() instead.")
    fun getStatementByIndex(index: Int): String {
        return statementsAsString[index]
    }

    fun size() : Int {
        return statements.size
    }

    @Deprecated(message = "This function will be deleted in version 2.0.0. Use size() instead.")
    fun getSize(): Int {
        return statementsAsString.size
    }

    @Deprecated(message = "This function will be deleted in version 2.0.0.")
    fun reformat(index: Int, vararg args: String) {
        statementsAsString[index] = String.format(statementsAsString[index], *args)
    }
}
