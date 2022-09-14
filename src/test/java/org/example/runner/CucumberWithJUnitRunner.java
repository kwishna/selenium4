package org.example.runner;
/*
  Runner Class For Running With TestNG.

  Parallel Through Command Line :-
  java -cp .;<path to cucumber jar folder>/* io.cucumber.core.cli.Main --threads 4 -g parallel parallel
 */


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
				dryRun = false,
				monochrome = true,
				features = "src/test/java/features",
				glue = {"features.step_definitions"},
				tags = "@smoke",
				plugin = {
						"progress",
						"summary",
//		        	"pretty",
						"timeline:target/cucumber-reports",
						"usage:target/cucumber-reports/cucumber-usage.json",
						"html:target/cucumber-reports/cucumber-pretty",
						"json:target/cucumber-reports/cucumber.json",
						"pretty:target/cucumber-reports/cucumber-pretty.txt",
						"rerun:target/cucumber-reports/failures/failed_scenarios.txt",
						"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
				},
				snippets = CucumberOptions.SnippetType.CAMELCASE
		)

public class CucumberWithJUnitRunner {
}