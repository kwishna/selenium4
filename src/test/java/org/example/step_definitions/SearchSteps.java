package org.example.step_definitions;

import io.cucumber.java.DataTableType;
import io.cucumber.java.DocStringType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchSteps {

    //https://cucumber.io/docs/cucumber/configuration/?lang=java

    @Given("today is {iso8601Date}")
    public void today_is(LocalDate date) {
        System.out.println(date);
    }

    @Given("Selenium4 Webdriver {string} browser is started")
    public void selenium4_webdriver_is_started(String browser) {

        System.out.println("throw new io.cucumber.java8.PendingException();");
    }

    @When("I open {string} home page")
    public void i_open_home_page(String string) {

        System.out.println("throw new io.cucumber.java8.PendingException();");
    }

    @Then("I verify doc string parameter")
    public void i_verify_doc_string_parameter(String docString) {

        System.out.println("throw new io.cucumber.java8.PendingException();");
    }

    @Then("I should be navigated to {string} search page")
    public void i_should_be_navigated_to_search_page(String string) {

        System.out.println("throw new io.cucumber.java8.PendingException();");
    }

    @When("I enter a search {string} in search field")
    public void iEnterASearchInSearchField(String keyword) {

        System.out.println("throw new io.cucumber.java8.PendingException();");
    }

    @Then("I verify following data table:")
    public void i_verify_following_data_table(io.cucumber.datatable.DataTable dataTable) {

        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        System.out.println("throw new io.cucumber.java8.PendingException();");
    }
}
