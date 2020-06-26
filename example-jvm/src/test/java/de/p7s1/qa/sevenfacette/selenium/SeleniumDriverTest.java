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
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
public class SeleniumDriverTest {

  private static FDriver facetteDriver;
  public static final String SCREENSHOT_OUTPUT_DIRECTORY = "./build/screenshots/";
  public static final String TESTING_URL = "https://spring-petclinic-community.herokuapp.com/";

  @BeforeAll
  static void setup() {
    facetteDriver = new FDriver(FDFactory.driver(Driver.CHROME, ""));
    //facetteDriver = new FDriver(FDFactory.driver(Driver.REMOTE, "http://localhost:4444/wd/hub"));
  }

  @AfterAll
  static void tearDown() {
    facetteDriver.quit();
  }

  @Test
  void startFacetteBrowser() {
    facetteDriver.get(TESTING_URL);
  }

  @Test
  void makeScreenshot() throws IOException {
    facetteDriver.get(TESTING_URL);
    File screeny = facetteDriver.getScreenshotAs(OutputType.FILE);
    FileUtils.copyFile(screeny, new File(SCREENSHOT_OUTPUT_DIRECTORY + "REMOTE_FDriverTest.png"));
  }

  @Test
  void clickLink() throws IOException {
    facetteDriver.get(TESTING_URL);
    facetteDriver.findElement(By.xpath("//a[@href='/owners/find']")).click();
    File screeny = facetteDriver.getScreenshotAs(OutputType.FILE);
    FileUtils.copyFile(screeny, new File(SCREENSHOT_OUTPUT_DIRECTORY + "ClickLinkTest.png"));
  }

  @Test
  void clickButton() throws IOException {
    facetteDriver.get(TESTING_URL);
    facetteDriver.findElement(By.xpath("//a[@href='/owners/find']")).click();
    facetteDriver.findElement(By.xpath("//button[@class='btn btn-default']")).click();
    File screeny = facetteDriver.getScreenshotAs(OutputType.FILE);
    FileUtils.copyFile(screeny, new File(SCREENSHOT_OUTPUT_DIRECTORY + "ClickButtonTest.png"));
  }

  @Test
  void writeText() throws IOException {
    facetteDriver.get(TESTING_URL);
    facetteDriver.findElement(By.xpath("//a[@href='/owners/find']")).click();
    facetteDriver.findElement(By.id("lastName")).sendKeys("Davis");
    facetteDriver.findElement(By.xpath("//button[@class='btn btn-default']")).click();
    File screeny = facetteDriver.getScreenshotAs(OutputType.FILE);
    FileUtils.copyFile(screeny, new File(SCREENSHOT_OUTPUT_DIRECTORY + "WriteTextTest.png"));
  }
}
