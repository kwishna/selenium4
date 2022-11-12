package org.example.hooks;

import io.cucumber.java.*;
import org.example.drivers.WebDriversManager;

public class Hooks {

    @AfterAll
    public static void afterAll() {
        System.out.println("This will run before the Suite");
    }

    @BeforeAll
    public static void beforeAll() {
        System.out.println("This will run after the Suite");
    }

    @Before(value = "@smoke", order = 0)
    public void beforeScenario(Scenario scenario) {
        System.out.println(scenario.getName());
        System.out.println("This will run before the Scenario");
    }

    @After(value = "@smoke", order = 1)
    public void afterScenario(Scenario scenario) {
        System.out.println(scenario.getName());
        System.out.println("This will run after the Scenario");
    }

    @After(value = "@QuiteBrowserAfter", order = 0)
    public void afterScenarioQuitBrowser(Scenario scenario) {
        System.out.println(scenario.getName());
        WebDriversManager.quitDriver();
        System.out.println("This will run after the Scenario");
    }

    @BeforeStep
    public void beforeStep(Scenario scenario) {
        System.out.println(scenario.getName());
        System.out.println("This will run before the Step");
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        System.out.println(scenario.getName());
        System.out.println("This will run after the Step");
    }
}
