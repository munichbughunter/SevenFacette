package de.p7s1.qa.sevenfacette.db

import java.util.*

actual class DbStatements {

    actual val statements: MutableList<String> = mutableListOf()

    actual fun add(statement: String): Boolean {
        return statements.add(statement)
    }

    actual val list: List<String>
        get() = Collections.unmodifiableList(statements)


    actual operator fun contains(statement: String): Boolean {
        return statement in statements
    }

    actual operator fun get(index: Int): String {
        return statements[index]
    }

    actual fun size(): Int {
        return statements.size
    }

    actual fun reformat(index: Int, vararg args: String) {
        statements[index] = String.format(statements[index], args)
    }
}
