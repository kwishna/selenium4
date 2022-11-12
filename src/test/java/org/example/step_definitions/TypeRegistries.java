package org.example.step_definitions;

import io.cucumber.java.DataTableType;
import io.cucumber.java.DocStringType;
import io.cucumber.java.ParameterType;
import org.example.models.Amount;
import org.example.models.Color;
import org.example.models.Grocery;
import org.example.models.Price;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TypeRegistries {

    @DataTableType
    public Grocery defineGrocery(Map<String, String> entry) {
        return new Grocery(entry.get("name"), Price.fromString(entry.get("price")));
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

    @ParameterType("red|blue|yellow")  // regexp
    public Color color(String color) {  // type, name (from method)
        return new Color(color);       // transformer function
    }
}
