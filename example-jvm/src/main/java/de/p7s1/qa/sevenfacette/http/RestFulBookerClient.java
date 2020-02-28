package de.p7s1.qa.sevenfacette.http;

import de.p7s1.qa.sevenfacette.sevenfacetteHttp.GenericHttpClient;
import de.p7s1.qa.sevenfacette.sevenfacetteHttp.Url;
import de.p7s1.qa.sevenfacette.sevenfacetteHttp.httpHeader;
import de.p7s1.qa.sevenfacette.sevenfacetteHttp.httpResponse;

public class RestFulBookerClient extends GenericHttpClient {
  public RestFulBookerClient() {
    super.url(new Url().baseUrl("localhost").port(3001));
  }

  public httpResponse getAllBookings() {
    return this.get("booking", new httpHeader());
  }

  public httpResponse getBookingByID(String bookingID) {
    return this.get("booking/" + bookingID, new httpHeader());
  }

  public httpResponse createNewBooking(String bookingData, httpHeader header) {
    return this.post("booking", bookingData, header);
  }

  public httpResponse deleteBooking(String bookingId, httpHeader headers) {
    return this.delete("/booking/" + bookingId, headers);
  }

  public httpResponse auth() {
    String content = "{\"username\":\"admin\",\"password\":\"password123\"}";
    return this.post("auth", content, new httpHeader());
  }
}
