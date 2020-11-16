package utils


import de.p7s1.qa.sevenfacette.utils.Files
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Testingclass for [Files].
 *
 * Testcases:
 * - generate data out of files (isNotNull)
 * - is the output the right datatype
 *
 * @author Stella Bastug
 */
class FilesTest {
    // relative path specifically for the test case
    private val RESOURCES_TEST_FOLDER_PATH = "src/jvmTest/resources/testfiles/"



    @Test
    fun getAsByteArrayNotNullTest(){

        val byteArray = Files.getAsByteArray(RESOURCES_TEST_FOLDER_PATH + "test.txt")

        assertNotNull(byteArray)
    }

    @Test
    fun getAsByteArrayTypeTest(){
        val byteArray = Files.getAsByteArray(RESOURCES_TEST_FOLDER_PATH + "testStringFile.txt")
        val expectedByteArray = "This is a test string for converted type testing!".toByteArray()
        assertEquals(expectedByteArray.contentToString(), byteArray.contentToString())
    }

    @Test
    fun getAsTextNotNullTest(){
        val textString = Files.getAsText(RESOURCES_TEST_FOLDER_PATH + "test.txt")

        assertNotNull(textString)
    }

    @Test
    fun getAsTextTypeTest(){
        val textString = Files.getAsText(RESOURCES_TEST_FOLDER_PATH + "testStringFile.txt")
        val expectedTextString = "This is a test string for converted type testing!"
        assertEquals(expectedTextString, textString)
    }

    @Test // FAILED with java.lang.IllegalStateException
    fun getResourceStreamNotNullTest(){
        val resourceStream = Files.getResourceStream(RESOURCES_TEST_FOLDER_PATH + "test.txt")

        assertNotNull(resourceStream)
    }

    @Test
    fun getResourceNotNullTest(){
        val resource = Files.getResource( "testfiles/test.txt")

        assertNotNull(resource)
    }

    @Test
    fun getResourceTextNotNullTest(){
        val resourceText = Files.getRessourceText( "testfiles/test.txt")

        assertNotNull(resourceText)
    }
}
