/*
package features.step_definitions;

import io.cucumber.java8.En;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class SearchStepsJ8 implements En {
    public SearchStepsJ8() {

        DataTableType((Map<String, String> row) -> new SearchStepsJ8.Entry(
                Integer.valueOf(row.get("first")),
                Integer.valueOf(row.get("second")),
                row.get("operation")));

        DataTableType((Map<String, String> row) -> new SearchStepsJ8.Grocery(
                row.get("name"),
                SearchStepsJ8.Price.fromString(row.get("price"))));

        ParameterType("amount", "\\d+\\.\\d+\\s[a-zA-Z]+", (String value) -> {
            String[] arr = value.split("\\s");
            return new Amount(new BigDecimal(arr[0]), Currency.getInstance(arr[1]));
        });

        DocStringType("shopping_list", (String docstring) -> {
            return Stream.of(docstring.split("\\s"))
                    .map(Grocery::new)
                    .toArray(Grocery[]::new);
        });

        Given("Selenium4 Webdriver {string} browser is started", (String string) -> {
            // Write code here that turns the phrase above into concrete actions
            System.out.println("throw new io.cucumber.java8.PendingException();");
        });

        When("I open {string} home page", (String string) -> {
            // Write code here that turns the phrase above into concrete actions
            System.out.println("throw new io.cucumber.java8.PendingException();");
        });

        Then("I verify doc string pramater", (String docString) -> {
            // Write code here that turns the phrase above into concrete actions
            System.out.println("throw new io.cucumber.java8.PendingException();");
        });

        Then("I should be navigated to {string} search page", (String string) -> {
            // Write code here that turns the phrase above into concrete actions
            System.out.println("throw new io.cucumber.java8.PendingException();");
        });

        When("I enter a search keyword <keyword> in search field", () -> {
            // Write code here that turns the phrase above into concrete actions
            System.out.println("throw new io.cucumber.java8.PendingException();");
        });

        Then("I verify following data table:", (io.cucumber.datatable.DataTable dataTable) -> {
            // Write code here that turns the phrase above into concrete actions
            // For automatic transformation, change DataTable to one of
            // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
            // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
            // Double, Byte, Short, Long, BigInteger or BigDecimal.
            //
            // For other transformations you can register a DataTableType.
            System.out.println("throw new io.cucumber.java8.PendingException();");
        });
    }

    static class Grocery {

        private final String name;
        private Price price;

        public Grocery(String name) {
            this.name = name;
        }

        Grocery(String name, Price price) {
            this.name = name;
            this.price = price;
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

    static final class Entry {
        private final Integer first;
        private final Integer second;
        private final String operation;

        Entry(Integer first, Integer second, String operation) {
            this.first = first;
            this.second = second;
            this.operation = operation;
        }
    }
}
*/
