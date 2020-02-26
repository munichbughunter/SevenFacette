package de.p7s1.qa.sevenfacette.db

import de.p7s1.qa.sevenfacette.DbStatements
import de.p7s1.qa.sevenfacette.helper.FileLoader
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.UncheckedIOException
import java.util.*

class ScriptReader {
    private lateinit var scriptPath: String
    private var fileLoader: FileLoader? = null
    private var scriptParser = ScriptParser()

    fun ScriptReader(scriptPath: String) {
        this.scriptPath = scriptPath
    }

    fun getStatements(sqlScript: String): DbStatements? {
        try {
            fileLoader
                    ?.loadInputStreamFromResource(scriptPath, sqlScript).use { inputStream ->
                        BufferedReader(InputStreamReader(inputStream)).use { reader ->
                            val statementList: List<String> = readScript(reader)
                            return scriptParser.parseScript(statementList)
                        }
                    }
        } catch (ioe: IOException) {
            throw UncheckedIOException(ioe)
        }
    }

    private fun readScript(reader: BufferedReader): List<String> {
        val statementList: MutableList<String> = ArrayList()
        try {
            var line: String
            while (reader.readLine().also { line = it } != null) {
                statementList.add(line)
            }
        } catch (ex: IOException) {
            throw UncheckedIOException(ex)
        }
        return statementList
    }
}