package http.auth

import de.p7s1.qa.sevenfacette.config.ConfigReader
import de.p7s1.qa.sevenfacette.http.Url
import de.p7s1.qa.sevenfacette.http.auth.AuthenticationFactory
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
class AuthenticationTest {

    @Test
    fun checkBasicAuthentication() {
        val authMap : MutableMap<String, String> = mutableMapOf()
        authMap["type"] = "basic"
        authMap["username"] = "test"
        authMap["password"] = "test"
        authMap["realm"] = "test"
        val authProvider = AuthenticationFactory(authMap).getAuthentication()
        assertTrue(authProvider.sendWithoutRequest)
    }

    @Test
    fun checkBasicAuthenticationWithDefaultValues() {
        val authMap : MutableMap<String, String> = mutableMapOf()
        authMap["type"] = "basic"
        val authProvider = AuthenticationFactory(authMap).getAuthentication()
        assertTrue(authProvider.sendWithoutRequest)
    }

    @Test
    fun checkBasicAuthenticationWithoutRequestFalse() {
        val authMap : MutableMap<String, String> = mutableMapOf()
        authMap["type"] = "basic"
        authMap["sendWithoutRequest"] = "false"
        val authProvider = AuthenticationFactory(authMap).getAuthentication()
        assertFalse(authProvider.sendWithoutRequest)
    }

    @Test
    fun checkOauthOne() {
        val authMap : MutableMap<String, String> = mutableMapOf()
        authMap["type"] = "oauth1"
        authMap["consumer_key"] = "test"
        authMap["access_token"] = "test"
        authMap["secret"] = "test"
        val authProvider = AuthenticationFactory(authMap).getAuthentication()
        assertTrue(authProvider.sendWithoutRequest)
    }

    @Test
    fun checkOauthOneWithDefaultValues() {
        val authMap : MutableMap<String, String> = mutableMapOf()
        authMap["type"] = "oauth1"
        val authProvider = AuthenticationFactory(authMap).getAuthentication()
        assertTrue(authProvider.sendWithoutRequest)
    }

    @Test
    fun checkOauthOneWithoutRequestFalse() {
        val authMap : MutableMap<String, String> = mutableMapOf()
        authMap["type"] = "oauth1"
        authMap["sendWithoutRequest"] = "false"
        val authProvider = AuthenticationFactory(authMap).getAuthentication()
        assertFalse(authProvider.sendWithoutRequest)
    }

    @Test
    fun checkOauthTwo() {
        val authMap : MutableMap<String, String> = mutableMapOf()
        authMap["type"] = "oauth2"
        authMap["bearer"] = "test"
        val authProvider = AuthenticationFactory(authMap).getAuthentication()
        assertTrue(authProvider.sendWithoutRequest)
    }

    @Test
    fun checkOauthTwoWithDefaultValues() {
        val authMap : MutableMap<String, String> = mutableMapOf()
        authMap["type"] = "oauth2"
        val authProvider = AuthenticationFactory(authMap).getAuthentication()
        assertTrue(authProvider.sendWithoutRequest)
    }

    @Test
    fun checkOauthTwoWithoutRequestFalse() {
        val authMap : MutableMap<String, String> = mutableMapOf()
        authMap["type"] = "oauth2"
        authMap["sendWithoutRequest"] = "false"
        val authProvider = AuthenticationFactory(authMap).getAuthentication()
        assertFalse(authProvider.sendWithoutRequest)
    }

    @Test
    fun checkNoValueType() {
        val authMap : MutableMap<String, String> = mutableMapOf()
        authMap["test"] = "test"
        assertFailsWith<Exception> {
            val authProvider = AuthenticationFactory(authMap).getAuthentication()
        }
    }

    @Test
    fun checkUnknownType() {
        val authMap : MutableMap<String, String> = mutableMapOf()
        authMap["type"] = "test"
        assertFailsWith<Exception> {
            val authProvider = AuthenticationFactory(authMap).getAuthentication()
        }
    }
}
