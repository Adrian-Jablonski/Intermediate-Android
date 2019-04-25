package com.example.stockmarketapp.stock;

import java.io.Serializable;

public class AllSymbols implements Serializable {
    private String symbol;
    private String name;
    private boolean isEnabled;

    public AllSymbols() {

    }

    public AllSymbols(String symbol, String name, boolean isEnabled) {
        this.symbol = symbol;
        this.name = name;
        this.isEnabled = isEnabled;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
