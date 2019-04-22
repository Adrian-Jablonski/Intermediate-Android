package com.example.stockmarketapp.stock;

public class Symbols {
    private Quote[] stocks;
    private News[] news;
    private Chart[] chart;

    public News[] getNews() {
        return news;
    }

    public void setNews(News[] news) {
        this.news = news;
    }

    public Quote[] getStocks() {
        return stocks;
    }

    public void setStocks(Quote[] stocks) {
        this.stocks = stocks;
    }

    public Chart[] getChart() {
        return chart;
    }

    public void setChart(Chart[] chart) {
        this.chart = chart;
    }
}
