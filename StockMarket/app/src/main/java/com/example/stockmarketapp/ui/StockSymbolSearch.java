package com.example.stockmarketapp.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stockmarketapp.R;
import com.example.stockmarketapp.stock.AllSymbols;
import com.example.stockmarketapp.stock.Quote;
import com.example.stockmarketapp.stock.Symbols;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StockSymbolSearch extends AppCompatActivity {

    private Symbols symbols;
    AllSymbols[] allSymbols;
    List<AllSymbols> allSymbolList;
    private TextView stockSearchText;
    private ImageView stockSymbolSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_symbol_search);

        getAllStockSymbols();

        //TODO: Add keyup event to update search results while typing
        stockSearchText = findViewById(R.id.stockSearchText);
        stockSymbolSearch = findViewById(R.id.stockSymbolSearch);

        stockSymbolSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = (stockSearchText.getText() + "").toLowerCase();
                System.out.println(searchText);
                // Filter data
                int resultCount = 0;
                int i = 0;
                while (resultCount < 5 && i < allSymbols.length) {

                    AllSymbols symbol = allSymbolList.get(i);
                    String stockName = symbol.getName().toLowerCase();
                    String stockSymbol = symbol.getSymbol().toLowerCase();

                    if (stockName.startsWith(searchText) || stockSymbol.startsWith(searchText)) {
                        System.out.printf("%s %s%n", symbol.getName(), symbol.getSymbol());
                        resultCount += 1;
                    }

                    i++;
                }
            }
        });



    }


    private void getAllStockSymbols() {
        String symbolsURL = "https://api.iextrading.com/1.0/ref-data/symbols";
        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(symbolsURL)
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
                        System.out.println("JSON DATA. All Stock Symbols :" + jsonData);
                        symbols = parseSymbolData(jsonData);
                        allSymbols = symbols.getSymbolList();
                        allSymbolList = Arrays.asList(allSymbols);

                        //TODO: use symbols.getSymbolList() to create an autocomplete search feature

                        System.out.println(allSymbols.toString());
                        System.out.println("SYMBOL LENGTH: " + allSymbols.length);
                        System.out.println(allSymbols[0].getName());
                        System.out.println(allSymbols[4].getName());
                        System.out.println(allSymbols[4].getSymbol());
                        System.out.println(allSymbols[4].isEnabled());

                    } catch (IOException e) {
                        System.out.println("FAILED");
                    } catch (Exception e) {
                        System.out.println("EXCEPTION ERROR: " + e);
                    }
                }
            });
        }
    }

    private AllSymbols[] getSymbolList(String jsonData) throws JSONException {
        JSONArray data = new JSONArray(jsonData);

        AllSymbols[] allSymbols = new AllSymbols[data.length()];

        for (int i = 0; i < data.length(); i++) {
            AllSymbols symbol = new AllSymbols();
            JSONObject symbolObj = data.getJSONObject(i);
            symbol.setSymbol(symbolObj.getString("symbol"));
            symbol.setName(symbolObj.getString("name"));
            symbol.setEnabled(symbolObj.getBoolean("isEnabled"));

            allSymbols[i] = symbol;
        }
        return allSymbols;
    }

    private Symbols parseSymbolData(String jsonData) throws JSONException {
        Symbols symbols = new Symbols();

        symbols.setSymbolList(getSymbolList(jsonData));

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

}
