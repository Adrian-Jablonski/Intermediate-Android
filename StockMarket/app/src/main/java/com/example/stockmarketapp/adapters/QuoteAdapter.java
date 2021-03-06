package com.example.stockmarketapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stockmarketapp.R;
import com.example.stockmarketapp.databinding.StockSymbolItemBinding;
import com.example.stockmarketapp.stock.Chart;
import com.example.stockmarketapp.stock.News;
import com.example.stockmarketapp.stock.Quote;
import com.example.stockmarketapp.stock.Symbols;
import com.example.stockmarketapp.ui.MainActivity;
import com.example.stockmarketapp.ui.StockDetailsActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.ViewHolder> {

    private List<Quote> quotes;
    private Context context;
    private TextView stockPrice;
    private TextView stockChangePerc;
    private TextView stockChange;
    private ImageView companyImage;
    private Symbols symbols = new Symbols();

    public QuoteAdapter(List<Quote> quotes, Context context) {
        this.quotes = quotes;
        this.context = context;
    }

    @NonNull
    @Override
    public QuoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        StockSymbolItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.stock_symbol_item,
                        viewGroup,
                        false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Quote quote = quotes.get(i);
        viewHolder.stockSymbolItemBinding.setStock(quote);
//        viewHolder.itemView.setBackgroundColor(Color.parseColor(quote.getColor()));

        stockPrice = viewHolder.itemView.findViewById(R.id.stockPrice);
        stockChange = viewHolder.itemView.findViewById(R.id.stockChange);
        stockChangePerc = viewHolder.itemView.findViewById(R.id.stockChangePerc);
        companyImage = viewHolder.itemView.findViewById(R.id.companyImage);

        setChangeColor(Color.parseColor(quote.getColor()));

        Picasso.get().load(quote.getLogo()).into(companyImage); // Loads image from URL

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
           //TODO: Create activity for each stock to show news and other data when clicking on stock
           //TODO: Add intent and startActivity(intent) here to go to news section
            @Override
            public void onClick(View v) {
                showStockDetails(quote);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Binding variables
        public StockSymbolItemBinding stockSymbolItemBinding;
        // Constructor to do view lookups for each subview
        public ViewHolder(StockSymbolItemBinding stockLayoutBinding) {
            super(stockLayoutBinding.getRoot());
            stockSymbolItemBinding = stockLayoutBinding;
        }
    }

    private void setChangeColor(int color) {
        stockPrice.setTextColor(color);
        stockChange.setTextColor(color);
        stockChangePerc.setTextColor(color);
    }

    public void showStockDetails(Quote quote) {
        Intent intent = new Intent(context, StockDetailsActivity.class);
        List<Quote> quoteList = Arrays.asList(quote);

        intent.putExtra("quoteList", (Serializable) quoteList);
        intent.putExtra("stockSymbol", quote.getSymbol());
        intent.putExtra("companyName", quote.getCompanyName());

        List<News> newsList = Arrays.asList(quote.getNewsList());
        intent.putExtra("newsList", (Serializable) newsList);

        List<Chart> chartList = Arrays.asList(quote.getChartList());
        intent.putExtra("chartList", (Serializable) chartList);

        context.startActivity(intent);
    }
}
