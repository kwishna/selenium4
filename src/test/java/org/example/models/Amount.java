package org.example.models;

import java.math.BigDecimal;
import java.util.Currency;

public class Amount {
    private final BigDecimal price;
    private final Currency currency;

    public Amount(BigDecimal price, Currency currency) {
        this.price = price;
        this.currency = currency;
    }
}
