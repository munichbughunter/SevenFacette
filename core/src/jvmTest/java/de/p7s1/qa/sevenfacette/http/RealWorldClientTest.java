package de.p7s1.qa.sevenfacette.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * Example tests for restful booker client to display functionality of GenericHttpClient
 *
 * @author Florian Pilz
 */

// Do not use these test currently in pipeline until backend is available in pipeline
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RealWorldClientTest {

  @BeforeAll
  public void setConfigFile() {
    System.setProperty("FACETTE_CONFIG", "realWorldFacetteConfig.yml");
  }

  @Test
  public void getAllArticles() {
    HttpResponse response =
            new RealWorldClient().getAllArticles();
    System.out.println(response.getBody());
    System.out.println(response.getStatus());
  }
}

