package de.p7s1.qa.sevenfacette.http

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result;

class HttpRestBuilder(val url: String,
                      val body: String? = null,
                      val header: Map<String,String>? = null,
                      val contentType: String? = null) {

    /*
    val (request, response, result) = "https://httpbin.org/get"
        .httpGet()
        .responseString()

    when (result) {
        is Result.Failure -> {
            val ex = result.getException()
            println(ex)
        }
        is Result.Success -> {
            val data = result.get()
            println(data)
        }
    }
     */
    fun executeGet() {

        val httpAsync = url
                .httpGet()
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
                        }
                        is Result.Success -> {
                            val data = result.get()
                            println(data)
                        }
                    }
                }

            httpAsync.join()
    }

    fun executePost() {

    }
}


/*



private ContentType contentType;
  private String baseURI;
  private String path;
  private String body;
  private Map<String, String> header = new HashMap<>(0);

  public FacetteRestBuilder setContentType(ContentType contentType) {
    this.contentType = contentType;
    return this;
  }

  public FacetteRestBuilder setBaseURI(String baseURI) {
    this.baseURI = baseURI;
    return this;
  }

  public FacetteRestBuilder setPath(String path) {
    this.path = path;
    return this;
  }

  public FacetteRestBuilder setBody(String body) {
    this.body = body;
    return this;
  }

  public FacetteRestBuilder setHeader(Map<String, String> header) {
    this.header = header;
    return this;
  }

  public FacetteRestAction build() {
    return new FacetteRestAction(this);
  }

  ContentType getContentType() {
    return contentType;
  }

  String getBaseURI() {
    return baseURI;
  }

  String getPath() {
    return path;
  }

  String getBody() {
    return body;
  }

  Map<String,String> getHeader() {
    return header;
  }
 */