package com.ntc.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ntc.mobileapp.adapters.NewsAdapter;
import com.ntc.mobileapp.models.NewsItem;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ImageButton navHome, navSchedule, navPersonalData, navStudentEval,
            navTeacherEval, navAccountReceivable, navChangePassword;
    private LinearLayout allCategory, studentGovCategory, fiatLuxCategory;
    private RecyclerView newsFeedRecyclerView;
    private NewsAdapter newsAdapter;
    private String currentCategory = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize views
        initializeViews();
        setupRecyclerView();
        setupNavigationListeners();
        setupCategoryListeners();
        
        // Load initial news data
        loadNewsData();
    }

    private void initializeViews() {
        // Navigation buttons
        navHome = findViewById(R.id.nav_home);
        navSchedule = findViewById(R.id.nav_schedule);
        navPersonalData = findViewById(R.id.nav_personal_data);
        navStudentEval = findViewById(R.id.nav_student_eval);
        navTeacherEval = findViewById(R.id.nav_teacher_eval);
        navAccountReceivable = findViewById(R.id.nav_account_receivable);
        navChangePassword = findViewById(R.id.nav_change_password);

        // Category buttons
        allCategory = findViewById(R.id.allCategory);
        studentGovCategory = findViewById(R.id.studentGovCategory);
        fiatLuxCategory = findViewById(R.id.fiatLuxCategory);

        // RecyclerView
        newsFeedRecyclerView = findViewById(R.id.newsFeedRecyclerView);
    }

    private void setupRecyclerView() {
        newsAdapter = new NewsAdapter();
        newsFeedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsFeedRecyclerView.setAdapter(newsAdapter);
    }

    private void setupCategoryListeners() {
        allCategory.setOnClickListener(v -> filterNewsByCategory("All"));
        studentGovCategory.setOnClickListener(v -> filterNewsByCategory("Student Government"));
        fiatLuxCategory.setOnClickListener(v -> filterNewsByCategory("Fiat Lux"));
    }

    private void filterNewsByCategory(String category) {
        if (!currentCategory.equals(category)) {
            currentCategory = category;
            newsAdapter.filterByCategory(category);
            
            // Update visual selection state
            updateCategorySelection();
        }
    }

    private void updateCategorySelection() {
        // Reset all categories to default state
        allCategory.setAlpha(0.7f);
        studentGovCategory.setAlpha(0.7f);
        fiatLuxCategory.setAlpha(0.7f);

        // Highlight selected category
        switch (currentCategory) {
            case "All":
                allCategory.setAlpha(1.0f);
                break;
            case "Student Government":
                studentGovCategory.setAlpha(1.0f);
                break;
            case "Fiat Lux":
                fiatLuxCategory.setAlpha(1.0f);
                break;
        }
    }

    private void loadNewsData() {
        // Sample news data - replace with real data from your backend
        List<NewsItem> newsItems = new ArrayList<>();
        
        // All category news
        newsItems.add(new NewsItem(
            "Welcome to the New Semester",
            "Start of classes for Academic Year 2024-2025. Important dates and reminders for all students.",
            "https://example.com/welcome.jpg",
            "All",
            "March 15, 2024"
        ));

        // Student Government news
        newsItems.add(new NewsItem(
            "Student Council Elections",
            "Cast your vote for the next student leaders. Voting period starts next week.",
            "https://example.com/election.jpg",
            "Student Government",
            "March 14, 2024"
        ));
        newsItems.add(new NewsItem(
            "Campus Organizations Fair",
            "Join various student organizations and discover new opportunities for growth.",
            "https://example.com/fair.jpg",
            "Student Government",
            "March 13, 2024"
        ));

        // Fiat Lux news
        newsItems.add(new NewsItem(
            "Literary Contest Winners",
            "Congratulations to the winners of the Annual Literary Competition.",
            "https://example.com/literary.jpg",
            "Fiat Lux",
            "March 12, 2024"
        ));
        newsItems.add(new NewsItem(
            "Art Exhibition Opening",
            "Student artworks on display at the campus gallery starting this weekend.",
            "https://example.com/art.jpg",
            "Fiat Lux",
            "March 11, 2024"
        ));

        newsAdapter.setNews(newsItems);
        updateCategorySelection();
    }

    private void setupNavigationListeners() {
        navHome.setOnClickListener(v -> handleNavigation("home"));
        navSchedule.setOnClickListener(v -> handleNavigation("schedule"));
        navPersonalData.setOnClickListener(v -> handleNavigation("personal_data"));
        navStudentEval.setOnClickListener(v -> handleNavigation("student_eval"));
        navTeacherEval.setOnClickListener(v -> handleNavigation("teacher_eval"));
        navAccountReceivable.setOnClickListener(v -> handleNavigation("account_receivable"));
        navChangePassword.setOnClickListener(v -> handleNavigation("change_password"));
    }

    private void handleNavigation(String destination) {
        switch (destination) {
            case "home":
                // Already on home
                break;
            case "schedule":
                startActivity(new Intent(this, ScheduleActivity.class));
                finish();
                break;
            case "personal_data":
                startActivity(new Intent(this, PersonalDataActivity.class));
                finish();
                break;
            case "student_eval":
                startActivity(new Intent(this, StudentEvaluationActivity.class));
                finish();
                break;
            case "teacher_eval":
                startActivity(new Intent(this, TeacherEvaluationActivity.class));
                finish();
                break;
            case "account_receivable":
                startActivity(new Intent(this, AccountReceivableActivity.class));
                finish();
                break;
            case "change_password":
                startActivity(new Intent(this, ChangePasswordActivity.class));
                finish();
                break;
        }
    }
} 