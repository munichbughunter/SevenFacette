package de.p7s1.qa.sevenfacette.selenium;


import de.p7s1.qa.sevenfacette.driver.FDFactory;
import de.p7s1.qa.sevenfacette.driver.FDFactory.Driver;
import de.p7s1.qa.sevenfacette.driver.FDriver;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
public class SeleniumDriverTest {

  private static FDriver facetteDriver;
  public static final String SCREENSHOT_OUTPUT_DIRECTORY = "./build/screenshots/";

  @BeforeAll
  static void setup() {
    System.setProperty("webdriver.chrome.driver","/Users/doe0003p/TestLab/p7s1-qa/os/SevenFacette/chromedriver");
    facetteDriver = new FDriver(FDFactory.driver(Driver.CHROME));
  }

  @AfterAll
  static void tearDown() {
    facetteDriver.quit();
  }

  @Test
  void startFacetteBrowser() {
    facetteDriver.get("https://www.google.de");
  }

  @Test
  void makeScreenshot() throws IOException {
    facetteDriver.get("https://www.google.de");
    File screeny = facetteDriver.getScreenshotAs(OutputType.FILE);
    FileUtils.copyFile(screeny, new File(SCREENSHOT_OUTPUT_DIRECTORY + "FDriverTest.png"));
  }
}
