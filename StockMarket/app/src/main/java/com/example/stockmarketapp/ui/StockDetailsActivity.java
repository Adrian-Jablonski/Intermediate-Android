package com.example.stockmarketapp.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.stockmarketapp.R;
import com.example.stockmarketapp.adapters.NewsAdapter;
import com.example.stockmarketapp.databinding.ActivityStockDetailsBinding;
import com.example.stockmarketapp.databinding.NewsListItemBinding;
import com.example.stockmarketapp.stock.News;
import com.example.stockmarketapp.stock.Quote;

import java.util.ArrayList;
import java.util.List;

public class StockDetailsActivity extends AppCompatActivity {

    private NewsAdapter adapter;
    private ActivityStockDetailsBinding binding;

    private TextView stockSymbolText;
    private TextView companyNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_stock_details);

        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_stock_details);


        stockSymbolText = findViewById(R.id.stockSymbol);
        companyNameText = findViewById(R.id.companyName);

        Intent intent = getIntent();
        List<News> newsList = (ArrayList<News>) intent.getSerializableExtra("newsList");
        List<Quote> quoteList = (ArrayList<Quote>) intent.getSerializableExtra("quoteList");
        Quote quote = quoteList.get(0);

        adapter = new NewsAdapter(newsList, this);

        binding.newsListItems.setAdapter(adapter);
        binding.newsListItems.setLayoutManager(new LinearLayoutManager(this));
        binding.newsListItems.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.newsListItems.setLayoutManager(new LinearLayoutManager(this));

        int newsListLen = newsList.size();

        for (int i = 0; i < newsListLen; i++) {
            System.out.printf("NEWS Item %s: %s%n", i, newsList.get(i).getSummary());
        }

        stockSymbolText.setText(quote.getSymbol());
        companyNameText.setText(quote.getCompanyName());
    }

}
