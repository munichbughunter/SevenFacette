package de.p7s1.qa.sevenfacette.selenium;

import static de.p7s1.qa.sevenfacette.driver.FDriver.getBrowser;

import de.p7s1.qa.sevenfacette.config.types.FacetteConfig;
import de.p7s1.qa.sevenfacette.core.JsExecutor;
import de.p7s1.qa.sevenfacette.core.Select;
import de.p7s1.qa.sevenfacette.driver.Browser;
import de.p7s1.qa.sevenfacette.driver.FElement;
import de.p7s1.qa.sevenfacette.driver.Page;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static de.p7s1.qa.sevenfacette.driver.FDriver.open;
import static de.p7s1.qa.sevenfacette.conditions.Have.text;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
public class SevenFacetteDriverTest {

  @BeforeAll
  static void setup() {
    System.setProperty("FACETTE_CONFIG", "seleniumTestConfig.yml");
  }

  @Test
  @Disabled
  void StartBrowser() {
    open(CalculatorPage::new)
      .calculate("10", "/", "2")
      .result.shouldHave(text("5"));
  }

  @Test
  @Disabled
  void JSScript() {
    open(CalculatorPage::new)
      .calculateWithJS("10", "/", "2")
      .result.shouldHave(text("5"));
  }

  @Test
  @Disabled
  void FileUploadLocalDriver() {
    open(UploadPage::new)
      .uploadImage()
      .message.shouldHave(text("1 file\n"+"has been successfully uploaded."));

    getBrowser().takeScreenshot("./build/screenshots/screeny.png");
  }

  @Test
  @Disabled
  void FileUploadRemoteDriver() {
    open(UploadPage::new)
      .uploadImage()
      .message.shouldHave(text("1 file\n"+"has been successfully uploaded."));
  }

  @Test
  @Disabled
  void takeScreenshotLocal() {
    getBrowser().takeScreenshot("./build/screenshots/screenyLocal.png");
  }

  @Test
  @Disabled
  void takeScreenshotRemote() {
    getBrowser().takeScreenshot("./build/screenshots/screenyRemote.png");
  }
}

class UploadPage extends Page {
  public UploadPage(Browser browser) {
    super(browser);
  }

  public String getUrl() {
    return "http://demo.guru99.com/test/upload/";
  }

  public FElement uploadFile = element(By.id("uploadfile_0"));
  public FElement submitButton = element(By.id("submitbutton"));
  public FElement message = element("#res");

  public UploadPage uploadImage() {
    this.uploadFile.setValue("/Users/doe0003p/TestLab/p7s1-qa/os/SevenFacette/example-jvm/screenshots/BaseImage.png");
    this.submitButton.click();
    return this;
  }
}

class CalculatorPage extends Page {
  public CalculatorPage(Browser browser) {
    super(browser);
  }

  public String getUrl() {
    //return "http://juliemr.github.io/protractor-demo/";
    // Here we can get the url directly from the config...
    return FacetteConfig.INSTANCE.getWeb().getBaseUrl();
  }

  public FElement first = element("input[ng-model='first']");
  public FElement second = element("input[ng-model='second']");
  public FElement goBtn = element("#gobutton");
  public FElement result = element("h2.ng-binding");
  public Select select = select("select[ng-model='operator']");

  public CalculatorPage calculate(String first, String operation, String second) {
    this.first.setValue(first);
    this.second.setValue(second);
    this.select.selectOption(operation);
    this.goBtn.click();
    return this;
  }

  public CalculatorPage takeElementSnappy() {
    getBrowser().shootElement("screenshots", "Element", this.goBtn.getWebElement());
    return this;
  }

  public CalculatorPage calculateWithJS(String first, String operation, String second) {
    this.first.setValue(first);
    this.second.setValue(second);
    this.select.selectOption(operation);
    JsExecutor jsExecutor = new JsExecutor(getBrowser().getDriver());
    jsExecutor.execute(new WebElement[]{goBtn.getWebElement()}, false, () -> "arguments[0].click();");
    return this;
  }
}
