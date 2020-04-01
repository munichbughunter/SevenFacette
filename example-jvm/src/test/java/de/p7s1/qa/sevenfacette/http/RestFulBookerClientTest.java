package de.p7s1.qa.sevenfacette.http;

import de.p7s1.qa.sevenfacette.sevenfacetteHttp.HttpHeader;
import de.p7s1.qa.sevenfacette.sevenfacetteHttp.HttpResponse;
import de.p7s1.qa.sevenfacette.sevenfacetteHttp.MultipartBody;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Example tests for restful booker client to display functionality of GenericHttpClient
 *
 * @author Florian Pilz
 */

public class RestFulBookerClientTest {

  @Test
  public void getAllBookings() {
    HttpResponse response =
            new RestFulBookerClient().getAllBookings();
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }

  @Test
  public void getBookingByID() {
    HttpResponse response =
            new RestFulBookerClient().getBookingByID("11");
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }

  @Test
  public void createNewBooking() throws IOException {
    String resource = "/http/example.json";
    InputStream input = this.getClass().getResourceAsStream(resource);
    String content = IOUtils.toString(input, StandardCharsets.UTF_8);

    HttpResponse response =
            new RestFulBookerClient().createNewBooking(content, new HttpHeader());
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }

  @Test
  public void deleteBooking() {
    HttpResponse response = new RestFulBookerClient().deleteBooking("11", new HttpHeader().add("Cookie", "token=46823e3cad43d25"));
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }

  @Test
  public void multiPartBody() {
    MultipartBody body = new MultipartBody()
            .addStringPart("firstPart", "My content")
            .addByteArrayPart("second Part", "My second content".getBytes());
    HttpResponse response = new RestFulBookerClient().sendMultipartData("", body, new HttpHeader());
    System.out.println(response.toString());
  }

  @Test
  public void auth() {
    HttpResponse response = new RestFulBookerClient().auth();
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }
}
