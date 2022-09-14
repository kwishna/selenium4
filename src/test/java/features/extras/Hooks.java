package features.extras;

import io.cucumber.java.*;

public class Hooks {

    @Before(value = "@smoke", order = 0)
    public void beforeScenario(Scenario scenario) {
        System.out.println(scenario.getName());
        System.out.println("This will run before the Scenario");
    }

    @After(value = "@smoke", order = 0)
    public void afterScenario(Scenario scenario) {
        System.out.println(scenario.getName());
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

    @AfterAll
    public static void afterAll() {
        System.out.println("This will run before the Suite");
    }

    @BeforeAll
    public static void beforeAll() {
        System.out.println("This will run after the Suite");
    }
}
