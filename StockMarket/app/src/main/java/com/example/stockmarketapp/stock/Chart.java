package com.example.stockmarketapp.stock;

import java.io.Serializable;

public class Chart implements Serializable {
    private String date;
    private double open;
    private double high;
    private double low;
    private double close;
    private double volume;
    private double change;
    private double changePercent;
    private String label;

    public Chart() {
    }

    public Chart(String date, double open, double high, double low, double close, double volume, double change, double changePercent, String label) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.change = change;
        this.changePercent = changePercent;
        this.label = label;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getOpen() {
        return (float) open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public float getHigh() {
        return (float) high;
    }

    public float getLow() {
        return (float) low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public float getClose() {
        return (float) close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(double changePercent) {
        this.changePercent = changePercent;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
