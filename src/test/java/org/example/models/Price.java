package org.example.models;

public class Price {
    private final int value;

    Price(int value) {
        this.value = value;
    }

    public static Price fromString(String value) {
        return new Price(Integer.parseInt(value));
    }
}
