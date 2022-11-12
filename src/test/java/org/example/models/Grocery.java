package org.example.models;

import java.util.Objects;

public class Grocery {

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
