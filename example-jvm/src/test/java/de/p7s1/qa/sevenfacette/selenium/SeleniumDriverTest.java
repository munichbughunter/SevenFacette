package de.p7s1.qa.sevenfacette.selenium;


import de.p7s1.qa.sevenfacette.driver.FDFactory;
import de.p7s1.qa.sevenfacette.driver.FDFactory.Driver;
import de.p7s1.qa.sevenfacette.driver.FDriver;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
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
    //facetteDriver = new FDriver(FDFactory.driver(Driver.CHROME, ""));
    //facetteDriver = new FDriver(FDFactory.driver(Driver.FIREFOX, ""));
    facetteDriver = new FDriver(FDFactory.driver(Driver.CHROME, "http://localhost:4444/wd/hub"));
    //facetteDriver = new FDriver(FDFactory.driver(Driver.FIREFOX, "http://localhost:4444/wd/hub"));
  }

  @AfterAll
  static void tearDown() {
    facetteDriver.quit();
  }

  @Test
  @Disabled
  void startFacetteBrowser() {
    facetteDriver.get(TESTING_URL);
  }

  @Test
  @Disabled
  void makeScreenshot() throws IOException {
    facetteDriver.get(TESTING_URL);
    File screeny = facetteDriver.getScreenshotAs(OutputType.FILE);
    FileUtils.copyFile(screeny, new File(SCREENSHOT_OUTPUT_DIRECTORY + "FDriverTest.png"));
  }

  @Test
  @Disabled
  void clickLink() throws IOException {
    facetteDriver.get(TESTING_URL);
    facetteDriver.findElement(By.xpath("//a[@href='/owners/find']")).click();
    File screeny = facetteDriver.getScreenshotAs(OutputType.FILE);
    FileUtils.copyFile(screeny, new File(SCREENSHOT_OUTPUT_DIRECTORY + "ClickLinkTest.png"));
  }

  @Test
  @Disabled
  void clickButton() throws IOException {
    facetteDriver.get(TESTING_URL);
    facetteDriver.findElement(By.xpath("//a[@href='/owners/find']")).click();
    facetteDriver.findElement(By.xpath("//button[@class='btn btn-default']")).click();
    File screeny = facetteDriver.getScreenshotAs(OutputType.FILE);
    FileUtils.copyFile(screeny, new File(SCREENSHOT_OUTPUT_DIRECTORY + "ClickButtonTest.png"));
  }

  @Test
  @Disabled
  void writeText() throws IOException {
    facetteDriver.get(TESTING_URL);
    facetteDriver.findElement(By.xpath("//a[@href='/owners/find']")).click();
    facetteDriver.findElement(By.id("lastName")).sendKeys("Davis");
    facetteDriver.findElement(By.xpath("//button[@class='btn btn-default']")).click();
    File screeny = facetteDriver.getScreenshotAs(OutputType.FILE);
    FileUtils.copyFile(screeny, new File(SCREENSHOT_OUTPUT_DIRECTORY + "WriteTextTest.png"));
  }

  @Test
  @Disabled
  void pageSnapshottingwithoutImagePath() {
    facetteDriver.get(TESTING_URL);
    facetteDriver.getPageSnapshot("");
  }

  @Test
  @Disabled
  void pageSnapshottingwithImagePath() {
    facetteDriver.get(TESTING_URL);
    facetteDriver.getPageSnapshot(SCREENSHOT_OUTPUT_DIRECTORY);
  }

  @Test
  @Disabled
  void elementSnapshotting() {
    facetteDriver.get(TESTING_URL);
    facetteDriver.findElement(By.xpath("//a[@href='/owners/find']")).click();
    facetteDriver.getElementSnapshot(facetteDriver.findElement(By.id("lastName")), SCREENSHOT_OUTPUT_DIRECTORY );
  }

  @Test
  @Disabled
  void screenComparisonFailure() {
    facetteDriver.get("https://www.google.de");
    facetteDriver.compareSnapshotWithDiff("testfiles/BaseImage.png", SCREENSHOT_OUTPUT_DIRECTORY , "actualFailure", 0.1);
  }

  @Test
  @Disabled
  void screenComparisonPass() {
    facetteDriver.get(TESTING_URL);
    facetteDriver.compareSnapshotWithDiff("testfiles/BaseImage.png", SCREENSHOT_OUTPUT_DIRECTORY, "actualPass", 0.1);
  }

  @Test
  @Disabled
  void screenComparison() {
    facetteDriver.get(TESTING_URL);
    facetteDriver.findElement(By.xpath("//a[@href='/owners/find']")).click();
    facetteDriver.compareSnapshotWithDiff("testfiles/BaseImage.png", SCREENSHOT_OUTPUT_DIRECTORY, "screenComparison", 0.1);
  }

  @Test
  @Disabled
  void FileUploadRemotDriver() {
    facetteDriver.setFileDetector();
    facetteDriver.get("http://demo.guru99.com/test/upload/");
    facetteDriver.findElement(By.id("uploadfile_0")).sendKeys("./screenshots/BaseImage.png");
    facetteDriver.findElement(By.id("submitbutton")).click();
    facetteDriver.getPageSnapshot("");
  }
}
