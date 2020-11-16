package fileTest

import de.p7s1.qa.sevenfacette.utils.FileReader
import kotlin.test.Test

class ReadFileTests {

    val path = "/Users/florianp/Projekte/Pro7/git_repos/SevenFacette/core/src/jsTest/resources/testFiles"

    @Test
    fun readFileAsText() {
        val file = "testFile.json"
        val text = FileReader().readFileAsString("${path}/$file")
        println(text)
    }

    @Test
    fun readFileAsByteArray() {
        val file = "testFile.json"
        val text = FileReader().readFileAsByteArray("${path}/$file")
        println(text is ByteArray)
    }

    @Test
    fun getPath() {
        println("TEST = " + FileReader().getPath("resources"))
    }

    @Test
    fun asdf() {
        FileReader()
    }
}