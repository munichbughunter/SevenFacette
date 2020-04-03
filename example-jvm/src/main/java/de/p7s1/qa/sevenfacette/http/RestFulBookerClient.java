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
public class RestFulBookerClient extends GenericHttpClient {
  public RestFulBookerClient() {
    Authentication auth = new BasicAuth("", "");
    this.setUrl(new Url().baseUrl("localhost").port(3001))
            .setProxy("localhost",8080)
            .setAuthentication(auth)
            .build();
  }

  public HttpResponse getAllBookings() {
    return this.get("booking", new HttpHeader());
  }

  public HttpResponse getBookingByID(String bookingID) {
    return this.get("booking/" + bookingID, new HttpHeader());
  }

  public HttpResponse createNewBooking(String bookingData, HttpHeader header) {
    return this.post("booking", bookingData, header);
  }

  public HttpResponse deleteBooking(String bookingId, HttpHeader headers) {
    return this.delete("/booking/" + bookingId, headers);
  }

  public HttpResponse sendMultipartData(String path, MultipartBody body, HttpHeader headers) {
    return this.postMultiPart(path, body, headers);
  }

  public HttpResponse auth() {
    String content = "{\"username\":\"admin\",\"password\":\"password123\"}";
    return this.post("auth", content, new HttpHeader());
  }

}
