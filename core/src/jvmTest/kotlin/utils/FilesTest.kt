package utils


import de.p7s1.qa.sevenfacette.utils.Files
import org.junit.Test
import kotlin.test.assertNotNull

/**
 * Testingclass for [Files].
 *
 * Testcases:
 * - generate data out of files
 * - is the output the right datatype
 *
 * @author Stella Bastug
 */
class FilesTest {
    // TODO Don't find the path
    private val RESOURCES_TEST_FILE_PATH = "testfiles/test.txt"

    @Test
    fun getAsByteArrayNotNullTest(){
        val byteArray = Files.getAsByteArray(RESOURCES_TEST_FILE_PATH)

        assertNotNull(byteArray)
    }

    @Test
    fun getAsByteArrayTypeTest(){
        val byteArray = Files.getAsByteArray(RESOURCES_TEST_FILE_PATH)

        //TODO assert(byteArray is ByteArray) is always true
    }

    @Test
    fun getAsTextNotNullTest(){
        val textString = Files.getAsText(RESOURCES_TEST_FILE_PATH)

        assertNotNull(textString)
    }

    @Test
    fun getAsTextTypeTest(){
        val textString = Files.getAsText(RESOURCES_TEST_FILE_PATH)

        //TODO assert(textString is String) is always true
    }

    @Test
    fun getResourceStreamTest(){}

    @Test
    fun getResourceTest(){}

    @Test
    fun getRessourceTextTest(){}
}