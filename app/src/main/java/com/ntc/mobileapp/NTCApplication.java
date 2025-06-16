package com.ntc.mobileapp;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;

public class NTCApplication extends Application {
    private static final String TAG = "NTCApplication";
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        Log.d(TAG, "Firebase initialized successfully");

        // Initialize Firebase Analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Log.d(TAG, "Firebase Analytics initialized successfully");

        // Log app open event
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.SCREEN_NAME, "app_launch");
        params.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "NTCApplication");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, params);
        Log.d(TAG, "App open event logged to Firebase Analytics");
    }
} 