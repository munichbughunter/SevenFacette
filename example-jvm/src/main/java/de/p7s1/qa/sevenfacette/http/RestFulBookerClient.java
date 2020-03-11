package http;

import de.p7s1.qa.sevenfacette.sevenfacetteHttp.*;

import javax.sound.midi.SysexMessage;

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

  public HttpResponse createNewBooking(
          String bookingData,
          HttpHeader header) {
    System.out.println(bookingData);
    System.out.println(header);

    HttpResponse response =
            this.post(
                    "booking",
                    bookingData,
                    header);
    return response;
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
