package com.ntc.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ntc.mobileapp.adapters.PersonalDataPagerAdapter;

public class PersonalDataActivity extends AppCompatActivity {
    private ImageButton navHome, navSchedule, navPersonalData, navStudentEval, 
                       navTeacherEval, navAccountReceivable, navChangePassword;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private TextView greetingText, sectionText;
    private PersonalDataPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);

        initializeViews();
        setupNavigationListeners();
        setupTabs();
        loadUserInfo();
    }

    private void initializeViews() {
        // Initialize navigation buttons
        navHome = findViewById(R.id.nav_home);
        navSchedule = findViewById(R.id.nav_schedule);
        navPersonalData = findViewById(R.id.nav_personal_data);
        navStudentEval = findViewById(R.id.nav_student_eval);
        navTeacherEval = findViewById(R.id.nav_teacher_eval);
        navAccountReceivable = findViewById(R.id.nav_account_receivable);
        navChangePassword = findViewById(R.id.nav_change_password);

        // Initialize tabs and viewpager
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        // Initialize header text views
        greetingText = findViewById(R.id.greetingText);
        sectionText = findViewById(R.id.sectionText);
    }

    private void setupNavigationListeners() {
        navHome.setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });

        navSchedule.setOnClickListener(v -> {
            startActivity(new Intent(this, ScheduleActivity.class));
            finish();
        });

        navPersonalData.setOnClickListener(v -> {
            // Already in PersonalDataActivity
        });

        navStudentEval.setOnClickListener(v -> {
            // TODO: Implement Student Evaluation navigation
        });

        navTeacherEval.setOnClickListener(v -> {
            // TODO: Implement Teacher Evaluation navigation
        });

        navAccountReceivable.setOnClickListener(v -> {
            // TODO: Implement Account Receivable navigation
        });

        navChangePassword.setOnClickListener(v -> {
            // TODO: Implement Change Password navigation
        });
    }

    private void setupTabs() {
        // Disable user swipe
        viewPager.setUserInputEnabled(false);

        pagerAdapter = new PersonalDataPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        // Set up TabLayoutMediator with custom tab views
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // Create custom tab view
            ImageView imageView = (ImageView) LayoutInflater.from(this)
                    .inflate(R.layout.custom_tab_layout, tabLayout, false);

            // Set initial tab images (unselected state)
            switch (position) {
                case 0:
                    imageView.setImageResource(R.drawable.personaldata_notselected);
                    break;
                case 1:
                    imageView.setImageResource(R.drawable.contactinformation_notselected);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.incaseofemergency_notselected);
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.otherinformation_notselected);
                    break;
            }

            tab.setCustomView(imageView);
        }).attach();

        // Add tab selected listener to handle image changes
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ImageView imageView = (ImageView) tab.getCustomView();
                if (imageView != null) {
                    switch (tab.getPosition()) {
                        case 0:
                            imageView.setImageResource(R.drawable.personaldata);
                            break;
                        case 1:
                            imageView.setImageResource(R.drawable.contactinformation);
                            break;
                        case 2:
                            imageView.setImageResource(R.drawable.incaseofemergency);
                            break;
                        case 3:
                            imageView.setImageResource(R.drawable.otherinformation);
                            break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ImageView imageView = (ImageView) tab.getCustomView();
                if (imageView != null) {
                    switch (tab.getPosition()) {
                        case 0:
                            imageView.setImageResource(R.drawable.personaldata_notselected);
                            break;
                        case 1:
                            imageView.setImageResource(R.drawable.contactinformation_notselected);
                            break;
                        case 2:
                            imageView.setImageResource(R.drawable.incaseofemergency_notselected);
                            break;
                        case 3:
                            imageView.setImageResource(R.drawable.otherinformation_notselected);
                            break;
                    }
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Do nothing
            }
        });

        // Set initial tab and page
        viewPager.setCurrentItem(0, false);
        TabLayout.Tab firstTab = tabLayout.getTabAt(0);
        if (firstTab != null) {
            firstTab.select();
        }
    }

    private void loadUserInfo() {
        // TODO: Load user info from database
        // For now using placeholder data
        greetingText.setText("Hi, Israel Andy");
        sectionText.setText("BSIT 2-2");
    }
} 