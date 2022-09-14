package org.example.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
		features = {"@target/cucumber-reports/failures/failed_scenarios.txt"}, // Error If @ Is Not Mentioned!
		// Complete Path Of failed_scenario.txt File
		monochrome = true,
		tags = "@smoke",
		plugin = {
				"progress",
				"summary",
				"pretty",
				"timeline:target/cucumber-reports",
				"usage:target/cucumber-reports/cucumber-usage.json",
				"html:target/cucumber-reports/cucumber-pretty",
				"json:target/cucumber-reports/cucumber.json",
				"pretty:target/cucumber-reports/cucumber-pretty.txt",
				"rerun:target/cucumber-reports/failures/failed_scenarios.txt",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
		},
		snippets = CucumberOptions.SnippetType.CAMELCASE,
		glue = {"features.step_definitions"}
)
public class FailedScenarioRunner extends AbstractTestNGCucumberTests {
	@DataProvider(parallel = false) // true Or false
	@Override
	public Object[][] scenarios() {
		return super.scenarios();
	}

}