import de.p7s1.qa.sevenfacette.http.Url
import kotlin.test.assertEquals
import kotlin.test.Test

class UrlTest {
    val basePath = "myBasePath"

    @Test
    fun checkUrlCreation() {
        val path = "https://$basePath"
        val url: Url = Url().baseUrl(path)
        assertEquals(url.create(), path)
    }

    @Test
    fun checkUrlCreationWithPort() {
        val path = "https://$basePath"
        val url: Url = Url().baseUrl(path).port(1234)
        assertEquals(url.create(), "${path}:1234")
    }

    @Test
    fun checkUrlCreationWithProtocol() {
        val path = "$basePath"
        val url: Url = Url().baseUrl(path).protocol("http")
        assertEquals(url.create(), "http://$path")
    }

    @Test
    fun checkUrlCreationNoProtocol() {
        val path = "$basePath"
        val url: Url = Url().baseUrl(path)
        assertEquals(url.create(), "http://$path")
    }

    @Test
    fun checkUrlCreationWithPath() {
        val path = "https://$basePath"
        val url: Url = Url().baseUrl(path).path("test")
        assertEquals(url.create(), "${path}/test")
    }

    @Test
    fun checkUrlCreationWithAll() {
        val path = "$basePath"
        val url: Url = Url().baseUrl(path).protocol("https").port(1234).path("test")
        assertEquals(url.create(), "https://${path}:1234/test")
    }
}
