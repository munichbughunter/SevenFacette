package de.p7s1.qa.sevenfacette.http;

public class OffsetClient extends GenericHttpClient {
  private String basePath = "http://www.google.de";

  public OffsetClient() {
    super();
  }

  public void executeGet() {
    get(this.basePath);
  }
}


