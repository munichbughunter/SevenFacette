package utils


import de.p7s1.qa.sevenfacette.utils.Resource
import org.junit.Test
import java.io.File
import java.nio.file.Paths
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Testingclass for [Resource].
 *
 * Testcases:
 * - Can i get a resource (return isNotNull)
 * - Can i getting the right full path
 *
 * @author Stella Bastug
 */
class ResourceTest {
    // relative path specifically for the test case
    private val RESOURCES_TEST_FOLDER_PATH = File.separator + "src"+File.separator +"jvmTest"+File.separator +"resources"+File.separator +"testfiles"+File.separator +"test.txt"

    @Test
    fun getResourceTest(){
        val resource = Resource.get(RESOURCES_TEST_FOLDER_PATH)

        assertNotNull(resource)
    }

    @Test
    fun getFullPathTest(){
        //arrange
        val fullPath = Resource.getFullPath(RESOURCES_TEST_FOLDER_PATH)
        val expectedPath = Paths.get(System.getProperty("user.dir")).toString().plus(RESOURCES_TEST_FOLDER_PATH)
        //act
        val dirPathLength = Paths.get(System.getProperty("user.dir")).toString().length
        //assert
        assertEquals(expectedPath.substring(dirPathLength),fullPath.substring(dirPathLength))
    }

}
