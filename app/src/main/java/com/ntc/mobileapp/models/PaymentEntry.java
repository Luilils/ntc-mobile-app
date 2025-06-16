package com.ntc.mobileapp.models;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.PropertyName;
import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;

public class PaymentEntry {
    @DocumentId
    private String id;
    private String userId;
    @PropertyName("Date")
    private String date;
    private String amount;
    @PropertyName("Description")
    private String description;
    private String status;
    @ServerTimestamp
    private Date timestamp;

    // Empty constructor needed for Firestore
    public PaymentEntry() {}

    public PaymentEntry(String userId, String date, String amount, String description, String status) {
        this.userId = userId;
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
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

    public Date getTimestamp() {
        return timestamp;
    }
} 