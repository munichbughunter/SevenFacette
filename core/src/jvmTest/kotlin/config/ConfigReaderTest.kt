package config

import de.p7s1.qa.sevenfacette.config.ConfigReader
import org.junit.Test
import kotlin.test.assertNotNull

/**
 * Testingclass for [ConfigReader].
 *
 * Testcases:
 *
 * @author Stella Bastug
 */
class ConfigReaderTest {

    @Test
    fun readConfig(){
        val config = ConfigReader.readConfig()
        assertNotNull(config.sevenFacette)
    }
}
