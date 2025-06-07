package com.ntc.mobileapp.models;

public class NewsItem {
    private String title;
    private String description;
    private String imageUrl;
    private String category;
    private String date;

    public NewsItem(String title, String description, String imageUrl, String category, String date) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }
} 