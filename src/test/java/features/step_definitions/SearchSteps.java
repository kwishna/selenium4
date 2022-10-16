package features.step_definitions;

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

    @DataTableType
    public Grocery defineGrocery(Map<String, String> entry) {
        return new Grocery(entry.get("name"), SearchSteps.Price.fromString(entry.get("price")));
    }

    @ParameterType(name = "amount", value = "\\d+\\.\\d+\\s[a-zA-Z]+")
    public Amount defineAmount(String value) {
        String[] arr = value.split("\\s");
        return new Amount(new BigDecimal(arr[0]), Currency.getInstance(arr[1]));
    }

    @DocStringType(contentType = "shopping_list")
    public List<Grocery> defineShoppingList(String docstring) {
        return Stream.of(docstring.split("\\s")).map(Grocery::new).collect(Collectors.toList());
    }

    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
    public LocalDate iso8601Date(String year, String month, String day) {
        return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }

    @Given("today is {iso8601Date}")
    public void today_is(LocalDate date) {
        System.out.println(date);
    }

    @Given("Selenium4 Webdriver {string} browser is started")
    public void selenium4_webdriver_is_started(String browser) {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("throw new io.cucumber.java8.PendingException();");
    }

    @When("I open {string} home page")
    public void i_open_home_page(String string) {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("throw new io.cucumber.java8.PendingException();");
    }

    @Then("I verify doc string pramater")
    public void i_verify_doc_string_pramater(String docString) {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("throw new io.cucumber.java8.PendingException();");
    }

    @Then("I should be navigated to {string} search page")
    public void i_should_be_navigated_to_search_page(String string) {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("throw new io.cucumber.java8.PendingException();");
    }

    @When("I enter a search keyword <keyword> in search field")
    public void i_enter_a_search_keyword_keyword_in_search_field() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("throw new io.cucumber.java8.PendingException();");
    }

    @Then("I verify following data table:")
    public void i_verify_following_data_table(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        System.out.println("throw new io.cucumber.java8.PendingException();");
    }

    static class Grocery {

        private String name;
        private Price price;

        public Grocery(String name, Price price) {
            this.name = name;
            this.price = price;
        }

        public Grocery(String name) {
            this.name = name;
        }

        public Price getPrice() {
            return price;
        }

        public void setPrice(Price price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Grocery grocery = (Grocery) o;
            return Objects.equals(name, grocery.name);
        }

    }

    static final class Price {

        private final int value;

        Price(int value) {
            this.value = value;
        }

        static Price fromString(String value) {
            return new Price(Integer.parseInt(value));
        }

    }

    static final class Amount {

        private final BigDecimal price;
        private final Currency currency;

        public Amount(BigDecimal price, Currency currency) {
            this.price = price;
            this.currency = currency;
        }

    }

}
