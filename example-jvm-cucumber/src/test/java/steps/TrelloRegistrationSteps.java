package steps;

import io.cucumber.java8.En;

import static de.p7s1.qa.sevenfacette.driver.FDriver.open;

public class TrelloRegistrationSteps implements En {

    //TrelloPage trelloPage = null;

    public TrelloRegistrationSteps() {

        Given("^I am on Trello page$", () -> {
      //      trelloPage = open(TrelloPage::new);
        });

        When("^I click on registration button$", () -> {
        //    trelloPage.clickRegistrationButton();
        });

        Then("^Registration page opened$", () -> {

        });


    }
}
