package com.example.stockmarketapp.stock;

import java.io.Serializable;

public class News implements Serializable {
    private String datetime;
    private String headline;
    private String source;
    private String url;
    private String summary;

    public News() {
    }

    public News(String datetime, String headline, String source, String url, String summary) {
        this.datetime = datetime;
        this.headline = headline;
        this.source = source;
        this.url = url;
        this.summary = summary;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
