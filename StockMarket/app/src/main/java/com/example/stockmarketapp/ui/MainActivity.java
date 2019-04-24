package com.example.stockmarketapp.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.stockmarketapp.R;
import com.example.stockmarketapp.stock.Chart;
import com.example.stockmarketapp.stock.News;
import com.example.stockmarketapp.stock.Quote;
import com.example.stockmarketapp.stock.Symbols;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> stockSymbols = new ArrayList<String>(
            Arrays.asList("aapl", "fb", "msft", "goog", "vym", "cvx", "bac", "twtr", "jpm", "t", "aig"));

    private Symbols symbols;

    private Button showStockData;

    public ArrayList<String> getStockSymbols() {
        return stockSymbols;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showStockData = findViewById(R.id.showStockDataButton);

        // https://api.iextrading.com/1.0/stock/market/batch?symbols=aapl,fb&types=quote,news,chart&range=1m&last=5

        getStockPrices(joinList(stockSymbols));
    }

    public String joinList(List<String> stockSymbols) {
        String joined = "";
        for (String symbol : stockSymbols) {
            joined = joined + symbol + ",";
        }
        return joined.substring(0, joined.length() - 1);
    }

    public void getStockPrices(String stockSymbols) {

        String stockURL = " https://api.iextrading.com/1.0/stock/market/batch?symbols="
                + stockSymbols
                + "&types=quote,news,chart,logo&range=3m";

        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(stockURL)
                    .build();

            Call call = client.newCall(request);

            call.enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        System.out.println("JSON DATA :" + jsonData);

                        if (response.isSuccessful()) {
                            symbols = parseStockData(jsonData);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showStockData.performClick();
                                }
                            });
                        }
                    } catch (IOException e) {
                        System.out.println("FAILED");
                    } catch (JSONException e) {
                        System.out.println("JSON Exception caught: "+ e);
                    }
                }
            });
        }

    }

    private Quote[] getStockQuote(String jsonData) throws JSONException {
        JSONObject data = new JSONObject(jsonData);

        Quote[] quotes = new Quote[data.length()];

        for (int i = 0; i < data.length(); i++) {
            try {
                JSONObject jsonQuote = data.getJSONObject(stockSymbols.get(i).toUpperCase()).getJSONObject("quote");
                JSONObject jsonLogo = data.getJSONObject(stockSymbols.get(i).toUpperCase()).getJSONObject("logo");

                Quote quote = new Quote();
                setQuoteData(jsonQuote, jsonLogo, quote);

                JSONArray jsonNews = data.getJSONObject(stockSymbols.get(i).toUpperCase()).getJSONArray("news");
                News[] news = createNewsList(i, jsonNews);
                quote.setNewsList(news);

                JSONArray jsonChart = data.getJSONObject(stockSymbols.get(i).toUpperCase()).getJSONArray("chart");
                Chart[] chartData = createChartList(i, jsonChart);
                quote.setChartList(chartData);

                quotes[i] = quote;
            } catch (Exception e) {
                // Removes invalid stock symbols from stock symbol list
                stockSymbols.remove(i);
                i -= 1;
                System.out.println("Invalid Stock Symbol: " + e);
            }
        }
        return quotes;
    }

    private Symbols parseStockData(String jsonData) throws JSONException {
        Symbols symbols = new Symbols();

        symbols.setStocks(getStockQuote(jsonData));

        return symbols;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        else {
            Toast.makeText(this, "Sorry, network is unavailable", Toast.LENGTH_LONG).show();
        }
        return isAvailable;
    }

    public void showDataOnClick(View view) {
        List<Quote> quotes = Arrays.asList(symbols.getStocks());
        Intent intent = new Intent(this, StockDataActivity.class);

        intent.putExtra("quotesList", (Serializable) quotes);
        startActivity(intent);

    }

    private void setQuoteData(JSONObject jsonQuote, JSONObject jsonLogo, Quote quote) throws JSONException {
        quote.setSymbol(jsonQuote.getString("symbol"));
        quote.setCompanyName(jsonQuote.getString("companyName"));
        quote.setLatestPrice(jsonQuote.getDouble("latestPrice"));
        quote.setChange(jsonQuote.getDouble("change"));
        quote.setChangePercent(jsonQuote.getDouble("changePercent"));
        quote.setColor();
        quote.setSector(jsonQuote.getString("sector"));
        quote.setLogo(jsonLogo.getString("url"));
        quote.setOpen(jsonQuote.getDouble("open"));
        quote.setLow(jsonQuote.getDouble("low"));
        quote.setHigh(jsonQuote.getDouble("high"));
        quote.setLow52Week(jsonQuote.getDouble("week52Low"));
        quote.setHigh52Week(jsonQuote.getDouble("week52High"));
    }

    private News[] createNewsList(int i, JSONArray jsonNews) throws JSONException {
        int newsLen = jsonNews.length();
        News[] news = new News[newsLen];
        for (int j = 0; j < newsLen; j++) {
            News newsPiece = new News();
            JSONObject newsObj = jsonNews.getJSONObject(j);
            newsPiece.setDatetime(newsObj.getString("datetime"));
            newsPiece.setHeadline(newsObj.getString("headline"));
            newsPiece.setSource(newsObj.getString("source"));
            newsPiece.setUrl(newsObj.getString("url"));
            newsPiece.setSummary(newsObj.getString("summary"));

            news[j] = newsPiece;
        }
        return news;
    }

    private Chart[] createChartList(int i, JSONArray jsonChart) throws JSONException {
        int dataLen = jsonChart.length();
        Chart[] chartData = new Chart[dataLen];
        for (int j = 0; j < dataLen; j++) {
            Chart dayData = new Chart();
            JSONObject chartObj = jsonChart.getJSONObject(j);
            dayData.setDate(chartObj.getString("date"));
            dayData.setOpen(chartObj.getDouble("open"));
            dayData.setHigh(chartObj.getDouble("high"));
            dayData.setLow(chartObj.getDouble("low"));
            dayData.setClose(chartObj.getDouble("close"));
            dayData.setVolume(chartObj.getDouble("volume"));
            dayData.setChange(chartObj.getDouble("change"));
            dayData.setChangePercent(chartObj.getDouble("changePercent"));
            dayData.setLabel(chartObj.getString("label"));

            chartData[j] = dayData;
        }
        return chartData;
    }
}
