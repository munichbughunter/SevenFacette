package de.p7s1.qa.sevenfacette.selenium;


import de.p7s1.qa.sevenfacette.driver.FDFactory;
import de.p7s1.qa.sevenfacette.driver.FDFactory.Driver;
import de.p7s1.qa.sevenfacette.driver.FDriver;
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
    System.setProperty("webdriver.chrome.driver","/Users/doe0003p/TestLab/p7s1-qa/os/SevenFacette/chromedriver");

    FDriver facettedriver = new FDriver(FDFactory.driver(Driver.CHROME));
    facettedriver.get("https://www.google.de");
    facettedriver.quit();
  }
}
