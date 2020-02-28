package de.p7s1.qa.sevenfacette.db

import de.p7s1.qa.sevenfacette.DbStatements

class ScriptParser {
    private val COMMENT_LINE_CODE = "//"
    private val COMMENT_LINE_SQL = "--"
    private val COMMENT_BLOCK_START = "/*"
    private val COMMENT_BLOCK_END = "*/"
    private val COMMENT_BLOCK_REGEX = "\\/\\*(\\*(?!\\/)|[^*])*\\*\\/"
    private val DELIMITER = ";"

    private var blockCommentActive = false

    fun parseScript(statementList: List<String>): DbStatements? {
        val statements = DbStatements()
        var command = StringBuilder()
        for (statement in statementList) {
            val trimmedStatement = trimStatement(statement)
            if (trimmedStatement.length > 0) {
                command.append(trimmedStatement)
                if (trimmedStatement.endsWith(DELIMITER)) {
                    statements.add(command.toString())
                    command = StringBuilder()
                } else {
                    command.append(" ")
                }
            }
        }
        return statements
    }

    private fun trimStatement(statement: String): String {
        var statement = statement
        statement = statement.replace("\t".toRegex(), " ")
        statement = statement.replace("\n".toRegex(), " ")
        val indexOfCodeComment = getValidStringIndexOf(statement, COMMENT_LINE_CODE)
        val indexOfSqlComment = getValidStringIndexOf(statement, COMMENT_LINE_SQL)
        if (indexOfCodeComment < Int.MAX_VALUE || indexOfSqlComment < Int.MAX_VALUE) {
            statement = statement.substring(0, Math.min(indexOfCodeComment, indexOfSqlComment))
        }
        statement = statement.replace(COMMENT_BLOCK_REGEX.toRegex(), "")
        val indexOfBlockEndComment = getValidStringLastIndexOf(statement, COMMENT_BLOCK_END)
        if (blockCommentActive) {
            if (indexOfBlockEndComment < Int.MAX_VALUE) {
                statement = statement.substring(indexOfBlockEndComment)
                blockCommentActive = false
            } else {
                return ""
            }
        }
        val indexOfBlockStartComment = getValidStringIndexOf(statement, COMMENT_BLOCK_START)
        if (indexOfBlockStartComment < Int.MAX_VALUE) {
            statement = statement.substring(0, indexOfBlockStartComment)
            blockCommentActive = true
        }
        return statement.trim { it <= ' ' }
    }

    private fun getValidStringIndexOf(statement: String, str: String): Int {
        val index = statement.indexOf(str)
        return if (index >= 0) index else Int.MAX_VALUE
    }

    private fun getValidStringLastIndexOf(statement: String, str: String): Int {
        val index = statement.lastIndexOf(str)
        return if (index >= 0) index + str.length else Int.MAX_VALUE
    }
}