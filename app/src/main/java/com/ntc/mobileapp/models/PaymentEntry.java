package com.ntc.mobileapp.models;

public class PaymentEntry {
    private String date;
    private String amount;
    private String description;
    private String status;

    public PaymentEntry(String date, String amount, String description, String status) {
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public String getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
} 