package http;

import de.p7s1.qa.sevenfacette.sevenfacetteHttp.HttpHeader;
import de.p7s1.qa.sevenfacette.sevenfacetteHttp.HttpResponse;
import de.p7s1.qa.sevenfacette.sevenfacetteHttp.MultipartBody;
import org.junit.jupiter.api.Test;

// ToDo: Implement assertions
public class RestFulBookerClientTest {

  @Test
  public void getAllBookings() {
    HttpResponse response =
            new http.RestFulBookerClient().getAllBookings();
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }

  @Test
  public void getBookingByID() {
    HttpResponse response =
            new http.RestFulBookerClient().getBookingByID("11");
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }

  @Test
  public void createNewBooking() {
    HttpResponse response =
            new http.RestFulBookerClient().createNewBooking(
                    "{\"firstname\" : \"Test\",\"lastname\" : \"User\",\"totalprice\" : 111,\"depositpaid\" : true,\"bookingdates\":{\"checkin\" : \"2019-07-15\",\"checkout\" : \"2019-07-26\"},\"additionalneeds\" : \"Breakfast\"}"
                    , new HttpHeader()
            );
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }

  @Test
  public void deleteBooking() {
    HttpResponse response = new http.RestFulBookerClient().deleteBooking("11", new HttpHeader().add("Cookie", "token=46823e3cad43d25"));
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }

  @Test
  public void multiPartBody() {
    MultipartBody body = new MultipartBody()
            .addStringPart("firstPart", "My content")
            .addByteArrayPart("second Part", "My second content".getBytes());
    HttpResponse response = new http.RestFulBookerClient().sendMultipartData("", body, new HttpHeader());
    System.out.println(response.toString());
  }

  @Test
  public void auth() {
    HttpResponse response = new http.RestFulBookerClient().auth();
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }
}
