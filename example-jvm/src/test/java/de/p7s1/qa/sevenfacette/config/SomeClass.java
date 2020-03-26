package de.p7s1.qa.sevenfacette.config;

public class SomeClass {
  @Value({"User"})
  public String user;

  @Value({"Time"})
  public String time;

  @Value({"Warning"})
  public String warning;

  public String getUser() {
    return user;
  }

  public String getTime() {
    return time;
  }

  public String getWarning() {
    return warning;
  }
}
