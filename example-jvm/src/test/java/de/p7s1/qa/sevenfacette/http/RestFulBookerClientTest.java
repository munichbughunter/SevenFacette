package de.p7s1.qa.sevenfacette.http;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Example tests for restful booker client to display functionality of GenericHttpClient
 *
 * @author Florian Pilz
 */

// Do not use these test currently in pipeline until backend is available in pipeline
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestFulBookerClientTest {

  @BeforeAll
  public void setConfigFile() {
    System.setProperty("FACETTE_CONFIG", "restfulBookerClientTestConfig.yml");
  }

  @Test
  @Disabled
  public void getAllBookings() {
    HttpResponse response =
            new RestFulBookerClient().getAllBookings();
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }

  @Test
  @Disabled
  public void getBookingByID() {
    HttpResponse response =
            new RestFulBookerClient().getBookingByID("12");
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }

  @Test
  @Disabled
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
  @Disabled
  public void deleteBooking() {
    HttpResponse response = new RestFulBookerClient().deleteBooking("12", new HttpHeader().add("Cookie", "token=46823e3cad43d25"));
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }

  @Test
  @Disabled
  public void multiPartBody() {
    MultipartBody body = new MultipartBody()
            .addStringPart("firstPart", "My content")
            .addByteArrayPart("second Part", "My second content".getBytes());
    HttpResponse response = new RestFulBookerClient().sendMultipartData("", body, new HttpHeader());
    System.out.println(response.toString());
  }

  @Test
  @Disabled
  public void auth() {
    HttpResponse response = new RestFulBookerClient().auth();
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }
}

