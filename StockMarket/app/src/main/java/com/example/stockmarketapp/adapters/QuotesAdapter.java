package com.example.stockmarketapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.stockmarketapp.R;
import com.example.stockmarketapp.databinding.StockListItemBinding;
import com.example.stockmarketapp.stock.Quote;

import java.util.List;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder> {

    private List<Quote> quotes;
    private Context context;

    public QuotesAdapter(List<Quote> quotes, Context context) {
        this.quotes = quotes;
        this.context = context;
    }

    @NonNull
    @Override
    public QuotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        StockListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.stock_list_item,
                        parent,
                        false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Quote quote = quotes.get(i);
        viewHolder.stockListItemBinding.setQuote(quote);
    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // Binding variables    -- Needs to be enabled by adding dataBinding { enabled = true } to build.gradle
        public StockListItemBinding stockListItemBinding;

        // Constructor to do view lookups for each subview
        public ViewHolder(StockListItemBinding stockLayoutBinding) {
            super(stockLayoutBinding.getRoot());
            stockListItemBinding = stockLayoutBinding;
        }

    }
}
