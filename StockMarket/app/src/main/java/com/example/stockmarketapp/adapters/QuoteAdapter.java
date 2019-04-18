package com.example.stockmarketapp.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stockmarketapp.R;
import com.example.stockmarketapp.databinding.StockSymbolItemBinding;
import com.example.stockmarketapp.stock.Quote;

import java.util.List;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.ViewHolder> {

    private List<Quote> quotes;
    private Context context;

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
        Quote quote = quotes.get(i);
        viewHolder.stockSymbolItemBinding.setStock(quote);
        viewHolder.itemView.setBackgroundColor(Color.parseColor(quote.getColor()));
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
}
