package org.example.runner;
/*
  Runner Class For Running With TestNG.

  Parallel Through Command Line :-
  java -cp .;<path to cucumber jar folder>/* io.cucumber.core.cli.Main --threads 4 -g parallel parallel

  https://github.com/paawak/cucumber-jvm/tree/master/spring
 */

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        dryRun = false,
        monochrome = true,
        features = {"src/test/java/features"},
        glue = {"org.example.step_definitions", "org.example.hooks"},
        tags = "@smoke",
        plugin = {
//                "progress",
                "summary",
                "pretty",
                "timeline:target/cucumber-reports",
                "usage:target/cucumber-reports/cucumber-usage.json",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/cucumber.json",
                "pretty:target/cucumber-reports/cucumber-pretty.txt",
                "rerun:target/cucumber-reports/failures/failed_scenarios.txt",
                "message:target/results.ndjson",
//                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        snippets = CucumberOptions.SnippetType.CAMELCASE
)

public class CucumberWithTestNGRunnerTest extends AbstractTestNGCucumberTests {
    @DataProvider(parallel = false)
    @Override
    public Object[][] scenarios() {
        return super.scenarios();
    }

}
