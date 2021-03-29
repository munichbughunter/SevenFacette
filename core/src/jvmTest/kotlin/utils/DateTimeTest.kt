package utils

import de.p7s1.qa.sevenfacette.utils.DateTime
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Testingclass for [DateTime].
 *
 * Testcases:
 * - Is somthing given
 * - Is it the right time
 *
 * @author Stella Bastug
 */
class DateTimeTest {


    /**
     * Check if actual time is right, until the last two digits.
     */
//    @Test
//    fun actualTimeTest(){
//        //arrange
//        val realTime = System.currentTimeMillis()
//        //act
//        val resultTime = DateTime.now()
//        //assert
//        assertEquals(realTime/100, resultTime/100)
//    }

    @Test
    fun isTimeGivenTest(){
        //arrange and act
        val resultTime = DateTime.now()
        println(resultTime)
        //assert
        assertNotNull(resultTime)
    }
}
