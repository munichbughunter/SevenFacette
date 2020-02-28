package de.p7s1.qa.sevenfacette.http;

import de.p7s1.qa.sevenfacette.sevenfacetteHttp.HttpHeader;
import de.p7s1.qa.sevenfacette.sevenfacetteHttp.HttpResponse;
import org.junit.jupiter.api.Test;

// ToDo: Implement assertions
public class RestFulBookerClientTest {

  @Test
  void getAllBookings() {
    HttpResponse response = new RestFulBookerClient().getAllBookings();
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }

  @Test
  void getBookingByID() {
    HttpResponse response = new RestFulBookerClient().getBookingByID("11");
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }

  @Test
  void createNewBooking() {
    HttpResponse response = new RestFulBookerClient().createNewBooking(
      "{\"firstname\" : \"Test\",\"lastname\" : \"User\",\"totalprice\" : 111,\"depositpaid\" : true,\"bookingdates\":{\"checkin\" : \"2019-07-15\",\"checkout\" : \"2019-07-26\"},\"additionalneeds\" : \"Breakfast\"}"
      , new HttpHeader().add("Content-Type", "application/json")
    );
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }

  @Test
  void deleteBooking() {
    HttpResponse response = new RestFulBookerClient().deleteBooking("11", new HttpHeader().add("Cookie", "token=46823e3cad43d25"));
    System.out.println(response.getBody());
    System.out.println(response.getStatus());

  }

  @Test
  void auth() {
    HttpResponse response = new RestFulBookerClient().auth();
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }
}
