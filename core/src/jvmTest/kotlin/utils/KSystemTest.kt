package utils

import de.p7s1.qa.sevenfacette.utils.KSystem
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Testingclass for [KSystem].
 *
 * Testcases:
 * - Getting standard variables
 * - Setting new variables
 *
 * @author Stella Bastug
 */
class KSystemTest {

    @Test
    fun getStandardEnvTest() {
        val result: String? = KSystem.getEnv("JAVA_HOME")
        assertNotNull(result)
    }

    @Test // FAILED Can't set Env
    fun setEnvTest(){
        //arrange
        KSystem.setEnv("TEST_ENV","TEST_VAR")
        //act
        val result: String? = KSystem.getEnv("TEST_ENV")
        //assert
        assertEquals("TEST_VAR", result)
    }

   @Test
   fun getStandardPropertyTest() {
       val result: String? = KSystem.getProperty("java.vm.specification.name")
       assertEquals("Java Virtual Machine Specification", result)
   }

    @Test
    fun setPropertyTest(){
        //arrange
        KSystem.setProperty("TEST_PROP","TEST_VAR")
        //act
        val result: String? = KSystem.getProperty("TEST_PROP")
        //assert
        assertEquals("TEST_VAR", result)
    }


}
