package com.example.stockmarketapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
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

import com.example.stockmarketapp.R;
import com.example.stockmarketapp.adapters.QuoteAdapter;
import com.example.stockmarketapp.databinding.ActivityStockDataBinding;
import com.example.stockmarketapp.stock.Quote;

import java.util.ArrayList;
import java.util.List;

public class StockDataActivity extends AppCompatActivity  {
    private QuoteAdapter adapter;
    private ActivityStockDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
