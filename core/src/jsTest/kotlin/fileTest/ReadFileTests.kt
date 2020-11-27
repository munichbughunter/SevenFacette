package fileTest

import de.p7s1.qa.sevenfacette.utils.FileReader
import kotlin.test.Test

class ReadFileTests {

    val path = "[addPathToLocalRepo]/core/src/jsTest/resources/testFiles"

    @Test
    fun readFileAsText() {
        val file = "testFile.json"
        //println(FileReader().readFileAsString("${path}/$file"))
    }

    @Test
    fun readFileAsByteArray() {
        val file = "testFile.json"
        //val text = FileReader().readFileAsByteArray("${path}/$file")
        //println(text is ByteArray)
    }

    @Test
    fun getPath() {
       // println("TEST = " + FileReader().getPath("resources").matches("SevenFacette-core-test/resources$"))
    }
}