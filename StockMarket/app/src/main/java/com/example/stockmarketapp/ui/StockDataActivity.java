package com.example.stockmarketapp.ui;

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
import android.view.View;

import com.example.stockmarketapp.R;
import com.example.stockmarketapp.adapters.QuoteAdapter;
import com.example.stockmarketapp.databinding.ActivityStockDataBinding;
import com.example.stockmarketapp.stock.Quote;

import java.util.ArrayList;
import java.util.List;

public class StockDataActivity extends AppCompatActivity {
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
