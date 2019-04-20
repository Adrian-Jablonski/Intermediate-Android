package com.example.stockmarketapp.stock;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Quote implements Serializable {
    private String symbol;
    private String companyName;
    private double open;
    private double close;
    private double latestPrice;
    private double change;
    private double changePercent;
    private String color;
    private String news;
    private News[] newsList;
    DecimalFormat decimalFormat = new DecimalFormat("0.##");

    public Quote() {
    }

    public Quote(String symbol, String companyName, double open, double close, double latestPrice, double change, double changePercent, String news) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.open = open;
        this.close = close;
        this.latestPrice = latestPrice;
        this.change = change;
        this.changePercent = changePercent;
        this.news = news;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(double latestPrice) {
        this.latestPrice = latestPrice;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public News[] getNewsList() {
        return newsList;
    }

    public void setNewsList(News[] newsList) {
        this.newsList = newsList;
    }

    public String getChange() {
        String sign = "";
        if (change > 0) {
            sign = "+";
        }
        return sign + decimalFormat.format(change);
    }

    public void setChange(double change) {
        this.change = change;
    }

    public String getChangePercent() {
        String sign = "";
        if (change > 0) {
            sign = "+";
        }
        return "(" + sign + decimalFormat.format(changePercent * 100) + "%)";
    }

    public void setChangePercent(double changePercent) {
        this.changePercent = changePercent;
    }

    public String getColor() {
        return color;
    }

    public void setColor() {
        if (this.change > .5) {
            this.color = "#00FF00";
        }
        else if (this.change >0) {
            this.color = "#80FF00";
        }
        else if (this.change == 0) {
            this.color = "#FFFFFF";
        }
        else if (this.change > -.5) {
            this.color = "#FF4646";
        }
        else {
            this.color = "#FF0000";
        }

    }

}
