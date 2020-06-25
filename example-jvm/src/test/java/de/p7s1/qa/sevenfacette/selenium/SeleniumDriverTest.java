package de.p7s1.qa.sevenfacette.selenium;


import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
public class SeleniumDriverTest {
  @Test
  void startBrowser() {
    WebDriver driver = new FDriver( FDriverFactory.driver( FDriverFactory.Driver.CHROME ) );

  }
}
