package de.p7s1.qa.sevenfacette;

import de.p7s1.qa.sevenfacette.config.types.FacetteConfig;
import de.p7s1.qa.sevenfacette.core.JsExecutor;
import de.p7s1.qa.sevenfacette.core.Select;
import de.p7s1.qa.sevenfacette.driver.Browser;
import de.p7s1.qa.sevenfacette.driver.FElement;
import de.p7s1.qa.sevenfacette.driver.Page;
//import org.openqa.selenium.WebElement;

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
/*
class CalculatorPage extends Page {
//  public CalculatorPage(Browser browser) {
//    super(browser);
//  }

  public String getUrl() {
    //return "http://juliemr.github.io/protractor-demo/";
    // Here we can get the url directly from the config...
    return FacetteConfig.INSTANCE.getWeb().getBaseUrl();
  }

//  public FElement first = element("input[ng-model='first']");
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

  public CalculatorPage calculateWithJS(String first, String operation, String second) {
    this.first.setValue(first);
    this.second.setValue(second);
    this.select.selectOption(operation);
//    JsExecutor jsExecutor = new JsExecutor(getBrowser().getDriver());
    //jsExecutor.execute(new WebElement[]{goBtn.getWebElement()}, false, () -> "arguments[0].click();");
    return this;
  }
}

 */
