package de.p7s1.qa.sevenfacette.db

import java.util.Collections

class DbStatements {
    private val statements: MutableList<DbStatement> = mutableListOf()

    fun add(dbStatement: DbStatement) {
        statements.add(dbStatement)
    }

    val list: List<DbStatement>
        get() = Collections.unmodifiableList(statements)

    operator fun contains(dbStatement: DbStatement) : Boolean {
        return dbStatement in statements
    }

    operator fun get(index: Int) : DbStatement {
        return statements[index]
    }

    fun size() : Int {
        return statements.size
    }



//    val statements: MutableList<String> = mutableListOf()
//
//    fun add(statement: String): Boolean {
//        return statements.add(statement)
//    }
//
//    val list: List<String>
//        get() = Collections.unmodifiableList(statements)
//
//
//    operator fun contains(statement: String): Boolean {
//        return statement in statements
//    }
//
//    operator fun get(index: Int): String {
//        return statements[index]
//    }
//
//    fun size(): Int {
//        return statements.size
//    }
//
//    fun reformat(index: Int, vararg args: String) {
//        statements[index] = String.format(statements[index], *args)
//    }
}
