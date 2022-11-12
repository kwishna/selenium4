package org.example.step_definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.example.pages.Selenium4DevTools;
import org.example.pages.Selenium4Page;

/*
        1) Selenium Grid 4 - Standalone
        -------------------------------
        java -jar selenium-server-4.0.0-alpha-7.jar standalone

        ----------------
        2) Hub and Node
        ----------------
        java -jar selenium-server-4.0.0-alpha-7.jar hub
        java -jar selenium-server-4.0.0-alpha-7.jar node --detect-drivers true

        3) Selenium Grid 4 - Distributed
        ---------------------------------
        java -jar selenium-server-4.0.0-alpha-1.jar sessions
        java -jar selenium-server-4.0.0-alpha-1.jar distributor --sessions http://localhost:5556
        java -jar selenium-server-4.0.0-alpha-1.jar router --sessions http://localhost:5556 --distributor http://localhost:5553
 */

public class Selenium4Steps {

    @Given("User Navigates To URL: {string}")
    public void user_navigates_to_url(String url) {
        new Selenium4Page().openUrl(url);
    }

    @And("User clicks on 'Slow calculator'")
    public void userClicksOnSlowCalculator() {
        new Selenium4Page().clickSlowCalculator();
    }

    @Then("User quits The Browser")
    public void userQuitsTheBrowser() {
        new Selenium4Page().userQuitsTheBrowser();
    }

    @And("User takes screenshot of 'Calculator' element")
    public void userTakesScreenshotOfCalculatorElement() {
        new Selenium4Page().userTakesScreenshotOfCalculatorElement();
    }

    @And("User opens {string} in a new window")
    public void userOpensANewWindowOnTheBrowser(String url) {
        new Selenium4Page().userOpensWindowOnTheBrowser(url);
    }

    @And("User opens {string} in a new tab")
    public void userOpensANewTabOnTheBrowser(String url) {
        new Selenium4Page().userOpensTabOnTheBrowser(url);
    }

    @And("User verify total tabs opened are: {int}")
    public void userVerifyTotalTabsOpenedAre(int num) {
        new Selenium4Page().userVerifyTotalTabsOpenedAre(num);
    }

    @And("User print the location of the object")
    public void userPrintTheLocationOfTheObject() {
        new Selenium4Page().userPrintTheLocationOfTheObject();
    }

    @And("Login using relative locators")
    public void loginUsingRelativeLocators() {
        new Selenium4Page().loginUsingRelativeLocators();
    }

    @Given("User Set Up DevTools Geolocation")
    public void userSetUpDevToolsGeolocation() {
        new Selenium4DevTools().setGeolocationOverride();
    }
}
