package com.example.stockmarketapp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.stockmarketapp.R;
import com.example.stockmarketapp.adapters.QuoteAdapter;
import com.example.stockmarketapp.databinding.ActivityStockDataBinding;
import com.example.stockmarketapp.stock.AllSymbols;
import com.example.stockmarketapp.stock.Quote;
import com.example.stockmarketapp.stock.Symbols;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StockDataActivity extends AppCompatActivity  {
    private QuoteAdapter adapter;
    private ActivityStockDataBinding binding;
    private Symbols symbols;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getAllStockSymbols();

        Intent intent = getIntent();
        List<Quote> quotesList =
                (ArrayList<Quote>) intent.getSerializableExtra("quotesList");

        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_stock_data);

        adapter = new QuoteAdapter(quotesList, this);

        binding.stockQuoteItems.setAdapter(adapter);
        binding.stockQuoteItems.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.stockQuoteItems.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onBackPressed() {   // Prevents user from returning to MainActivity loading screen
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuItem menuItem = menu.add("Refresh");
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuItem.setIcon(R.drawable.ic_refresh_black_24dp);

//        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        System.out.println("CLICKED ID: " + id + " " + R.id.refresh);
        //noinspection SimplifiableIfStatement
        if (id == 0) {  // Refreshing stocks list
            System.out.println("REFRESHED");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                        AllSymbols[] allSymbols = symbols.getSymbolList();

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

    private Symbols parseSymbolData(String jsonData) throws JSONException {
        Symbols symbols = new Symbols();

        symbols.setSymbolList(getSymbolList(jsonData));

        return symbols;
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

    //    private List<Quote> getQuoteData() {    // TEST DATA
//        List<Quote> quotes = new ArrayList<>();
//
//        Quote quote = new Quote("AA", "A1", 2, 1.4, 1.3, -4, -.12);
//        quotes.add(quote);
//        quote = new Quote("AB", "A2", 3, 3.4, 5, 2, 2.2);
//        quotes.add(quote);
//        quote = new Quote("AC", "A3", 12, 12, 12, 0, 0);
//        quotes.add(quote);
//        quote = new Quote("AD", "A1", 2, 1.4, 1.3, -4, -.12);
//        quotes.add(quote);
//        quote = new Quote("AE", "A2", 3, 3.4, 5, 2, 2.2);
//        quotes.add(quote);
//
//        return quotes;
//    }

}
