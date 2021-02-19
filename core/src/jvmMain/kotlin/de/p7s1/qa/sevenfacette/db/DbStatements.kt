package de.p7s1.qa.sevenfacette.db

import java.util.Collections

actual class DbStatements {

    actual val statements: MutableList<String> = mutableListOf()

    @Deprecated(message = "This function will be deleted in version 2.0.0. Use SqlStatement instead.")
    actual fun add(statement: String) : Boolean {
        return statements.add(statement)
    }

    @Deprecated(message = "This function will be deleted in version 2.0.0. Use get which returns List<SqlStatement> instead.")
    actual val list: List<String>
        get() = Collections.unmodifiableList(statements)

    @Deprecated(message = "This function will be deleted in version 2.0.0. Use contains(sqlStatement: SqlStatement) instead.")
    actual operator fun contains(statement: String): Boolean {
        return statement in statements
    }

    @Deprecated(message = "This function will be deleted in version 2.0.0. Use get() which returns a SqlStatement instead.")
    actual operator fun get(index: Int): String {
        return statements[index]
    }

    actual fun size(): Int {
        return statements.size
    }

    @Deprecated(message = "This function will be deleted in version 2.0.0.")
    actual fun reformat(index: Int, vararg args: String) {
        statements[index] = String.format(statements[index], *args)
    }
}
