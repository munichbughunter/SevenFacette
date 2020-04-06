package de.p7s1.qa.sevenfacette.db

import org.slf4j.LoggerFactory
import java.util.Collections

actual class DbStatements {

    actual val statements: MutableList<String> = mutableListOf()


    actual fun add(statement: String): Boolean {
        return statements.add(statement)
    }

    actual val list: List<String>
        get() = Collections.unmodifiableList(statements)


    actual operator fun contains(statement: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual operator fun get(index: Int): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun size(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
