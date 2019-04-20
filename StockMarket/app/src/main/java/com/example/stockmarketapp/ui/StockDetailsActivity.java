package com.example.stockmarketapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.stockmarketapp.R;
import com.example.stockmarketapp.stock.Quote;

import java.util.ArrayList;
import java.util.List;

public class StockDetailsActivity extends AppCompatActivity {

    TextView stockSymbolText;
    TextView companyNameText;
    TextView newsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);

        stockSymbolText = findViewById(R.id.stockSymbol);
        companyNameText = findViewById(R.id.companyName);
        newsText = findViewById(R.id.news);

        Intent intent = getIntent();
        String stockSymbol = intent.getStringExtra("stockSymbol");
        String companyName = intent.getStringExtra("companyName");
        String news = intent.getStringExtra("news");

        stockSymbolText.setText(stockSymbol);
        companyNameText.setText(companyName);
        newsText.setText(news);
    }

}
