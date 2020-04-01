
import de.p7s1.qa.sevenfacette.http.Url
import org.junit.Test

class UrlTest {
    val basePath = "myBasePath"

    @Test
    fun checkUrlCreation() {
        val path = "https://$basePath"
        val url: Url = Url().baseUrl(path)
        assert(url.create() == path)
    }

    @Test
    fun checkUrlCreationWithPort() {
        val path = "https://$basePath"
        val url: Url = Url().baseUrl(path).port(1234)
        assert(url.create() == "${path}:1234")
    }

    @Test
    fun checkUrlCreationWithProtocol() {
        val path = "$basePath"
        val url: Url = Url().baseUrl(path).protocol("http")
        assert(url.create() == "http://$path")
    }

    @Test
    fun checkUrlCreationNoProtocol() {
        val path = "$basePath"
        val url: Url = Url().baseUrl(path)
        assert(url.create() == "http://$path")
    }

    @Test
    fun checkUrlCreationWithPath() {
        val path = "https://$basePath"
        val url: Url = Url().baseUrl(path).path("test")
        assert(url.create() == "${path}/test")
    }

    @Test
    fun checkUrlCreationWithAll() {
        val path = "$basePath"
        val url: Url = Url().baseUrl(path).protocol("https").port(1234).path("test")
        assert(url.create() == "https://${path}:1234/test")
    }
}
