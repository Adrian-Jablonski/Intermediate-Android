package com.example.stockmarketapp.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.stockmarketapp.R;
import com.example.stockmarketapp.databinding.NewsListItemBinding;
import com.example.stockmarketapp.stock.News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> news;
    private Context context;

    public NewsAdapter(List<News> news, Context context) {
        this.news = news;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        NewsListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.news_list_item,
                        viewGroup,
                        false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        News newsItem = news.get(i);
        viewHolder.newsListItemBinding.setNewsItem(newsItem);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Binding variables
        public NewsListItemBinding newsListItemBinding;

        //Constructor to do view lookups for each subview
        public ViewHolder(NewsListItemBinding newsLayoutBinding) {
            super(newsLayoutBinding.getRoot());
            newsListItemBinding = newsLayoutBinding;
        }

    }
}
