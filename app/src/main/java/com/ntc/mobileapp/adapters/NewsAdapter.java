package com.ntc.mobileapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.ntc.mobileapp.R;
import com.ntc.mobileapp.models.NewsItem;
import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<NewsItem> newsList;
    private List<NewsItem> allNews;

    public NewsAdapter() {
        this.newsList = new ArrayList<>();
        this.allNews = new ArrayList<>();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem newsItem = newsList.get(position);
        holder.bind(newsItem);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void setNews(List<NewsItem> news) {
        this.allNews = new ArrayList<>(news);
        this.newsList = new ArrayList<>(news);
        notifyDataSetChanged();
    }

    public void filterByCategory(String category) {
        newsList.clear();
        if (category.equals("All")) {
            newsList.addAll(allNews);
        } else {
            for (NewsItem news : allNews) {
                if (news.getCategory().equals(category)) {
                    newsList.add(news);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        private final ImageView newsImage;
        private final TextView newsTitle;
        private final TextView newsDate;
        private final TextView newsDescription;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImage = itemView.findViewById(R.id.newsImage);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            newsDate = itemView.findViewById(R.id.newsDate);
            newsDescription = itemView.findViewById(R.id.newsDescription);
        }

        public void bind(NewsItem newsItem) {
            newsTitle.setText(newsItem.getTitle());
            newsDate.setText(newsItem.getDate());
            newsDescription.setText(newsItem.getDescription());
            
            // Load image using Glide
            if (newsItem.getImageUrl() != null && !newsItem.getImageUrl().isEmpty()) {
                Glide.with(itemView.getContext())
                    .load(newsItem.getImageUrl())
                    .centerCrop()
                    .into(newsImage);
            }
        }
    }
} 