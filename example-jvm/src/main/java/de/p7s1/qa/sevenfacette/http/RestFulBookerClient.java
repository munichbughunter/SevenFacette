package de.p7s1.qa.sevenfacette.http;

import de.p7s1.qa.sevenfacette.sevenfacetteHttp.GenericHttpClient;
import de.p7s1.qa.sevenfacette.sevenfacetteHttp.HttpHeader;
import de.p7s1.qa.sevenfacette.sevenfacetteHttp.HttpResponse;
import de.p7s1.qa.sevenfacette.sevenfacetteHttp.Url;

public class RestFulBookerClient extends GenericHttpClient {
  public RestFulBookerClient() {
    super.url(new Url().baseUrl("localhost").port(3001));
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

  public HttpResponse auth() {
    String content = "{\"username\":\"admin\",\"password\":\"password123\"}";
    return this.post("auth", content, new HttpHeader());
  }
}
