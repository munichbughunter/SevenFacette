package de.p7s1.qa.sevenfacette.gql

/**
 * Represents a prepared GraphQL statement
 *
 * @param [gql] is the original GraphQL statement with placeholder
 * @param [args] are the arguments with the placeholder should be replaced
 *
 * @author Patrick Döring
 */
class GraphqlStatement(var gql: String, vararg args: Any?) {
    private var regex = "(?<!^')\\?(?!.*?')"

    init {
        if (args.isNotEmpty()) {
            replaceGqlPlaceholder(*args as Array<out Any>)
        }
    }

    /**
     * Validates if the GraphQL statement contains a placeholder
     *
     * @return [true] or [false]
     */
    fun validateGraphQlStatement() : Boolean{
        return !gql.contains(Regex(regex))
    }

    /**
     * Replaces placeholder with specific parameters
     *
     * @param [args] are the arguments with the placeholder should be replaced
    */
    fun replaceGqlPlaceholder(vararg args: Any?) {
        val parts: List<String> = gql.split(Regex(regex))
        val newList: MutableList<String> = mutableListOf()

        newList.add("{\"query\":\"")
        for (i in parts.indices) {
            newList.add(parts[i])
            if (i < parts.size - 1) {
                newList.add(formatParameter(args[i]))
            }
        }
        newList.add("\"}")
        gql = newList.joinToString(separator = "")
    }

   /**
     * If the argument is a String we use single quotes if not we use double quotes
     *
     * @param [arg] is the argument with the placeholder should be replaced
     */
    private fun formatParameter(arg: Any?) : String {
        return if (arg is String) {
            "\\\"$arg\\\""
        } else {
            "$arg"
        }
    }
}
