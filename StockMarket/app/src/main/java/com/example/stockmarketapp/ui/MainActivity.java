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
import com.example.stockmarketapp.stock.News;
import com.example.stockmarketapp.stock.Quote;
import com.example.stockmarketapp.stock.Symbols;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private String[] stockSymbols = {"aapl", "fb", "msft", "goog", "vym", "cvx", "bac", "twtr", "jpm", "t", "aig"};

    private Symbols symbols;

    private Button showStockData;

    public String[] getStockSymbols() {
        return stockSymbols;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showStockData = findViewById(R.id.showStockDataButton);

        // https://api.iextrading.com/1.0/stock/market/batch?symbols=aapl,fb&types=quote,news,chart&range=1m&last=5

        getStockPrices(joinArray(stockSymbols));
    }

    public String joinArray(String[] stockSymbols) {
        String joined = "";
        for (String symbol : stockSymbols) {
            joined = joined + symbol + ",";
        }
        return joined.substring(0, joined.length() - 1);
    }

    public void getStockPrices(String stockSymbols) {

        String stockURL = " https://api.iextrading.com/1.0/stock/market/batch?symbols="
                + stockSymbols
                + "&types=quote,news,chart&range=3m";

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

    private Symbols parseStockData(String jsonData) throws JSONException {
        Symbols symbols = new Symbols();

        int symbolLen = stockSymbols.length;

        symbols.setStocks(getStockQuote(jsonData, symbolLen));

        return symbols;
    }

    private Quote[] getStockQuote(String jsonData, int symbolLen) throws JSONException {
        JSONObject data = new JSONObject(jsonData);

        Quote[] quotes = new Quote[symbolLen];

        for (int i = 0; i < symbolLen; i++) {
            JSONObject jsonQuote = data.getJSONObject(stockSymbols[i].toUpperCase()).getJSONObject("quote");

            Quote quote = new Quote();

            quote.setSymbol(jsonQuote.getString("symbol"));
            quote.setCompanyName(jsonQuote.getString("companyName"));
            quote.setLatestPrice(jsonQuote.getDouble("latestPrice"));
            quote.setChange(jsonQuote.getDouble("change"));
            quote.setChangePercent(jsonQuote.getDouble("changePercent"));
            quote.setColor();

            //TODO: Currently saving only first news headline into Quote class. Find a way to pass all news info without an intent error when starting new activity. Try by setting up a news class to organize news info. Then pass News[] array into quote
            JSONArray jsonNews = data.getJSONObject(stockSymbols[i].toUpperCase()).getJSONArray("news");
            JSONObject newsPieceFirstNews = jsonNews.getJSONObject(0);

            quote.setNews(newsPieceFirstNews.getString("headline"));

            News[] news = createNewsList(i, jsonNews);
            quote.setNewsList(news);

            quotes[i] = quote;
        }
        return quotes;
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
}
