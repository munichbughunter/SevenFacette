package de.p7s1.qa.sevenfacette.http;


/**
 * Example application of generic http client
 * This example uses the RestfulBooker https://restful-booker.herokuapp.com/
 *
 * All specific clients (in this case the restful booker client) inherit the GenericHttpClient.
 * In the constructor the URL without paths, the authentication and the proxy are set. After that the build function needs to be executed to build the client.
 * Then the GenericHttpClient functions can be used to send requests to the client.
 * All functions need a path (even if the path is empty) and a HttpHeader object (even if no headers are attached) to the requests.
 * If a content should be sent this content needs to be added to the functions.
 *
 * Later use the functions created in the client for tests
 *
 * @author Florian Pilz
 */

public class RealWorldClient {

  private final GenericHttpClient client;

  public RealWorldClient() {
    client = HttpClientFactory.createClient("realWorld");
  }

  public HttpResponse getAllArticles() {
    return client.get("", new HttpHeader());
  }
}
