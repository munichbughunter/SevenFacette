package de.p7s1.qa.sevenfacette.http;

import de.p7s1.qa.sevenfacette.sevenfacetteHttp.httpHeader;
import de.p7s1.qa.sevenfacette.sevenfacetteHttp.httpResponse;
import org.junit.jupiter.api.Test;

// ToDo: Implement assertions
public class RestFulBookerClientTest {

  @Test
  void getAllBookings() {
    httpResponse response = new RestFulBookerClient().getAllBookings();
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }

  @Test
  void getBookingByID() {
    httpResponse response = new RestFulBookerClient().getBookingByID("11");
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }

  @Test
  void createNewBooking() {
    httpResponse response = new RestFulBookerClient().createNewBooking(
      "{\"firstname\" : \"Test\",\"lastname\" : \"User\",\"totalprice\" : 111,\"depositpaid\" : true,\"bookingdates\":{\"checkin\" : \"2019-07-15\",\"checkout\" : \"2019-07-26\"},\"additionalneeds\" : \"Breakfast\"}"
      , new httpHeader().add("Content-Type", "application/json")
    );
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }

  @Test
  void deleteBooking() {
    httpResponse response = new RestFulBookerClient().deleteBooking("11", new httpHeader().add("Cookie", "token=46823e3cad43d25"));
    System.out.println(response.getBody());
    System.out.println(response.getStatus());

  }

  @Test
  void auth() {
    httpResponse response = new RestFulBookerClient().auth();
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }
}