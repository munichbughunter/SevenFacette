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

public class RestFulBookerClient {

  private GenericHttpClient client;

  public RestFulBookerClient() {
    client = HttpClientFactory.createClient("restfulBooker");
  }

  public HttpResponse getAllBookings() {
    return client.get("booking", new HttpHeader());
  }

  public HttpResponse getBookingByID(String bookingID) {
    return client.get("booking/" + bookingID, new HttpHeader());
  }

  public HttpResponse createNewBooking(String bookingData, HttpHeader header) {
    return client.post("booking", bookingData, CONTENTTYPES.APPLICATION_JSON, header);
  }

  public HttpResponse deleteBooking(String bookingId, HttpHeader headers) {
    return client.delete("/booking/" + bookingId, headers);
  }

  public HttpResponse sendMultipartData(String path, MultipartBody body, HttpHeader headers) {
    return client.post(path, body, headers);
  }

  public HttpResponse auth() {
    String content = "{\"username\":\"admin\",\"password\":\"password123\"}";
    return client.post("auth", content, CONTENTTYPES.APPLICATION_JSON, new HttpHeader());
  }

}
