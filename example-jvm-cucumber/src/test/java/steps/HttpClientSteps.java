package steps;

import de.p7s1.qa.sevenfacette.http.HttpResponse;
import http.RestFulBookerClient;
import io.cucumber.java8.En;

import static org.junit.Assert.assertEquals;

public class HttpClientSteps implements En {

    private HttpResponse response;

    public HttpClientSteps() {
        When("^I query all my bookings$", () -> {
            response = new RestFulBookerClient().getAllBookings();
        });

        Then("^I receive at least one responses$", () -> {
           assert(response.getBody().split("bookingid").length > 1);
        });

        Then("^I get http status 200$", () -> {
           assertEquals(response.getStatus(), 200);
        });
    }

}
