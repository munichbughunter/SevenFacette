package de.p7s1.qa.sevenfacette

actual class DbStatements {
    actual val statements: MutableList<String> = mutableListOf()
    actual val list: List<String> = listOf()

    actual fun add(statement: String): Boolean {
        return statements.add(statement)
    }

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
