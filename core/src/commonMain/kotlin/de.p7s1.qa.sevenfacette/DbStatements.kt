package de.p7s1.qa.sevenfacette

expect class DbStatements {
    val statements: MutableList<String>
    val list: List<String>

    fun add(statement: String): Boolean

    operator fun contains(statement: String): Boolean

    operator fun get(index: Int): String

    fun size(): Int
}
