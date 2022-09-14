package org.example.runner;

import static io.cucumber.junit.platform.engine.Constants.*;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectDirectories;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
//@SelectDirectories("src/test/java/features")
//@SelectClasspathResource("io/github/bonigarcia")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "features.step_definitions")
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "src/test/java/features")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@smoke")
public class CucumberJunit5Test {

}
/*
Alternate Ways To Pass Cucumber Parameters:-
1) mvn -Dtest="org.example.runner.CucumberJunit5Test" test
2) mvn clean install -Dcucumber.glue="features.step_definitions" -Dcucumber.features="src/test/java/features"
3) junit-platform.properties
*/
