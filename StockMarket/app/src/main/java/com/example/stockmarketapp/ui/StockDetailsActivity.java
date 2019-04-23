package com.example.stockmarketapp.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.stockmarketapp.R;
import com.example.stockmarketapp.adapters.NewsAdapter;
import com.example.stockmarketapp.databinding.ActivityStockDetailsBinding;
import com.example.stockmarketapp.stock.Chart;
import com.example.stockmarketapp.stock.News;
import com.example.stockmarketapp.stock.Quote;
import com.example.stockmarketapp.view.ChartView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StockDetailsActivity extends AppCompatActivity {

    private NewsAdapter adapter;
    private ActivityStockDetailsBinding binding;

    private TextView stockSymbolText;
    private TextView companyNameText;
    private TextView sectorText;
    private TextView stockPrice;
    private TextView change;
    private TextView changePerc;
    private TextView openPrice;
    private TextView dayRange;
    private TextView range52Week;
    private ImageView companyImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_stock_details);

        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_stock_details);


        stockSymbolText = findViewById(R.id.stockSymbol);
        companyNameText = findViewById(R.id.companyName);
        companyImage = findViewById(R.id.companyImage);
        sectorText = findViewById(R.id.sector);
        stockPrice = findViewById(R.id.stockPrice);
        change = findViewById(R.id.stockChange);
        changePerc = findViewById(R.id.stockChangePerc);
        openPrice = findViewById(R.id.openPrice);
        dayRange = findViewById(R.id.dayRange);
        range52Week = findViewById(R.id.range52Week);

        Intent intent = getIntent();
        List<Quote> quoteList = (ArrayList<Quote>) intent.getSerializableExtra("quoteList");
        List<News> newsList = (ArrayList<News>) intent.getSerializableExtra("newsList");
        List<Chart> chartList = (ArrayList<Chart>) intent.getSerializableExtra("chartList");
        Quote quote = quoteList.get(0);

        adapter = new NewsAdapter(newsList, this);

        binding.newsListItems.setAdapter(adapter);
        binding.newsListItems.setLayoutManager(new LinearLayoutManager(this));
        binding.newsListItems.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.newsListItems.setLayoutManager(new LinearLayoutManager(this));

        stockSymbolText.setText(quote.getSymbol());
        companyNameText.setText(quote.getCompanyName());
        sectorText.setText(quote.getSector());
        stockPrice.setText(quote.getLatestPrice() + "");
        change.setText(quote.getChange());
        changePerc.setText(quote.getChangePercent());
        openPrice.setText(quote.getOpen() + "");
        dayRange.setText(quote.getLow() + " - " + quote.getHigh());
        range52Week.setText(quote.getLow52Week() + " - " + quote.getHigh52Week());

        setChangeColor(Color.parseColor(quote.getColor()));

        Picasso.get().load(quote.getLogo()).into(companyImage); // Loads image from URL

        int chartListLen = chartList.size();

//        showDataInChartUsingGraphView(chartList, quote, chartListLen);
        showDataInChart(chartList, quote, chartListLen);
    }

    private void setChangeColor(int color) {
        stockPrice.setTextColor(color);
        change.setTextColor(color);
        changePerc.setTextColor(color);
    }

//    private void showDataInChartUsingGraphView(List<Chart> chartList, Quote quote, int chartListLen) {    //Also removed graphView from .xml file
//        GraphView graph = findViewById(R.id.stockGraph);
//        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
//
//        System.out.printf("CHART LIST: %s%n", quote.getSymbol());
//        for (int i = 0; i < chartListLen; i++) {
//            System.out.printf("Date: %s: Close: %s%n", chartList.get(i).getDate(), chartList.get(i).getClose());
//            series.appendData(new DataPoint(i, chartList.get(i).getClose()), true, chartListLen);
//        }
//
//        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.parseColor("#50A6C2"));
//        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.parseColor("#50A6C2"));
//        graph.getGridLabelRenderer().setGridColor(Color.parseColor("#C0C0C0"));
//
//        graph.addSeries(series);
//    }

    private void showDataInChart(List<Chart> chartList, Quote quote, int chartListLen) {
        ChartView chartView = new ChartView(this, chartList);
        RelativeLayout relativeLayout = findViewById(R.id.stockGraph);
        relativeLayout.addView(chartView);

    }

}
