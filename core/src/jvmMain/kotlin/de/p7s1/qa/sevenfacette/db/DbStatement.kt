package de.p7s1.qa.sevenfacette.db

/**
 * Represents a prepared database statement
 *
 * @param [statement] is the original statement with placeholder
 * @param [args] are the arguments with the placeholder should be replaced
 *
 * @author Patrick Döring
 */
class DbStatement(statement: String, vararg args: Any?) {
    val origStatement: String = statement
    var sqlStatement: String = statement
    var arguments: Any = args
    //var regex = "(?<!^|'|\\r\\n|\\n)\\?(?!'|\$|\\r\\n|\\n)"
    var regex = "(?<!^')\\?(?!.*?')"

    init {
        if (args.isNotEmpty()) {
            replaceAll(*args as Array<out Any>)
        }
    }

    /**
     * Validates if the statement contains a placeholder
     *
     * @return [true] or [false]
     */
    fun validate() : Boolean{
       // return !sqlStatement.contains("?")
        return !sqlStatement.contains(Regex(regex))
    }

    /**
     * Replaces placeholder with specific parameters
     *
     * @param [args] are the arguments with the placeholder should be replaced
     */
    fun replaceAll(vararg args: Any?) {
        arguments = args
        val parts: List<String> = sqlStatement.split(Regex(regex))

        val newList: MutableList<String> = mutableListOf()

        for (i in 0 until parts.size - 1) {
            newList.add(parts[i])
            newList.add(formatParameter(args[i]))
        }
        sqlStatement = newList.joinToString(separator = "")
    }

    /**
     * If the argument is a String we use single quotes if not we use double quotes
     *
     * @param [arg] is the argument with the placeholder should be replaced
     */
    private fun formatParameter(arg: Any?) : String {
        return if (arg is String) {
            "'$arg'"
        } else {
            "$arg"
        }
    }
}
