package de.p7s1.qa.sevenfacette.db

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
class DbStatement(statement: String, vararg args: Any?) {
    val origStatement: String = statement
    var sqlStatement: String = statement
    var arguments: Any = args

    init {
        if (args.isNotEmpty()) {
            replaceAll(*args as Array<out Any>)
        }
    }

    fun validate() : Boolean{
        return !sqlStatement.contains("?")
    }

    fun replaceAll(vararg args: Any) {
        arguments = args
        val parts: List<String> = sqlStatement.split("?")

        val newList: MutableList<String> = mutableListOf()

        for (i in 0 until parts.size - 1) {
            newList.add(parts[i])
            newList.add(formatParameter(args[i]))
        }
        sqlStatement = newList.joinToString(separator = "")
    }

    private fun formatParameter(arg: Any?) : String {
        return if (arg is String) {
            "'$arg'"
        } else {
            "$arg"
        }
    }
}
