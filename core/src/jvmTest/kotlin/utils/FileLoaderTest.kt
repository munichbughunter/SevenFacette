package utils

import de.p7s1.qa.sevenfacette.utils.FileLoader
import org.junit.Test
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

/**
 * Testingclass for [FileLoader].
 *
 * Testcases:
 * - load file
 * - file not empty
 *
 * @author Stella Bastug
 */
class FileLoaderTest {

    private val RESOURCES_TEST_FOLDER = "testfiles/"

    @Test
    fun fileStreamIsNotEmpty(){
        //arrange
        val loader = FileLoader()
        val inputStream = loader.loadFileFromResourceAsStream(RESOURCES_TEST_FOLDER, "test.txt")
        //act
        val result = BufferedReader(InputStreamReader(inputStream!!)).lines()
                .parallel().collect(Collectors.joining("\n"))
        //assert
        assert(result.isNotEmpty())
    }

    @Test
    fun readFileFromResourceAsStream(){
        //arrange
        val loader = FileLoader()
        //act
        val inputStream = loader.loadFileFromResourceAsStream(RESOURCES_TEST_FOLDER, "test.txt")
        //assert
        assertNotNull(inputStream)
    }

    @Test
    fun canNotReadResourceFileAsString() {
        //arrange
        val loader = FileLoader()
        // act and assert
        assertFailsWith<Exception> {
            loader.loadFileFromResourceAsString(RESOURCES_TEST_FOLDER, "tesst.txt")
        }
    }

    @Test
    fun fileStringIsNotEmpty() {
        //arrange
        val loader = FileLoader()
        //act
        val fileContent = loader.loadFileFromResourceAsString(RESOURCES_TEST_FOLDER, "test.txt")
        //assert
        assert(fileContent!!.isNotEmpty())
    }

}