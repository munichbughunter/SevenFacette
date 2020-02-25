package de.p7s1.qa.sevenfacette.http;

public class httpClient extends GenericHttpClient {
  private String basePath = "http://www.google.de";

  public void get() {
    httpClient.get(basePath);
  }

  public OffsetClient(RestServiceAuth auth) {
    super(auth);
  }

  private String basePath = "https://offsetreset-generic.sg.pke.fhm.de/api/v1/";

  public void get() {
    this.get(basePath);
    this.suspendedGet(basePath);
  }

}


