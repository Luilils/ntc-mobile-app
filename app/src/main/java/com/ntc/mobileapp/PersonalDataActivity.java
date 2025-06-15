package com.ntc.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ntc.mobileapp.adapters.PersonalDataPagerAdapter;
import com.ntc.mobileapp.models.PersonalData;

public class PersonalDataActivity extends AppCompatActivity {
    private ImageButton navHome, navSchedule, navPersonalData, navStudentEval, 
                       navTeacherEval, navAccountReceivable, navChangePassword;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private TextView greetingText, sectionText;
    private PersonalDataPagerAdapter pagerAdapter;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private PersonalData personalData;

    private static final String TAG = "PersonalDataActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        initializeViews();
        setupNavigationListeners();
        setupTabs();
        loadPersonalDataFromFirestore();
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
        navHome.setOnClickListener(v -> handleNavigation("home"));
        navSchedule.setOnClickListener(v -> handleNavigation("schedule"));
        navPersonalData.setOnClickListener(v -> handleNavigation("personal_data"));
        navStudentEval.setOnClickListener(v -> handleNavigation("student_eval"));
        navTeacherEval.setOnClickListener(v -> handleNavigation("teacher_eval"));
        navAccountReceivable.setOnClickListener(v -> handleNavigation("account_receivable"));
        navChangePassword.setOnClickListener(v -> handleNavigation("change_password"));
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

    private void loadPersonalDataFromFirestore() {
        Log.d(TAG, "loadPersonalDataFromFirestore: called");
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            Log.d(TAG, "loadPersonalDataFromFirestore: Current user ID: " + userId);
            db.collection("users").document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "loadPersonalDataFromFirestore: Document found for user: " + userId);
                            personalData = document.toObject(PersonalData.class);
                            if (personalData != null) {
                                Log.d(TAG, "loadPersonalDataFromFirestore: PersonalData object created successfully.");
                                // Update header text views
                                greetingText.setText("Hi, " + (personalData.getFullName() != null ? personalData.getFullName().split("\\s+")[0] : "User"));
                                sectionText.setText(personalData.getCourseAndSection());

                                // Pass data to pager adapter
                                if (pagerAdapter != null) {
                                    Log.d(TAG, "loadPersonalDataFromFirestore: Passing data to pager adapter.");
                                    pagerAdapter.updatePersonalData(personalData);
                                }
                            } else {
                                Log.e(TAG, "PersonalData object is null after conversion");
                                Toast.makeText(this, "Failed to parse personal data.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d(TAG, "loadPersonalDataFromFirestore: No such document for user: " + userId);
                            Toast.makeText(this, "Personal data not found.", Toast.LENGTH_SHORT).show();
                            // Optionally, navigate to a profile creation page or show a message
                            // For now, keep placeholder or empty fields
                            greetingText.setText("Hi, User");
                            sectionText.setText("N/A");
                        }
                    } else {
                        Log.e(TAG, "Failed to load personal data: ", task.getException());
                        Toast.makeText(this, "Error loading personal data.", Toast.LENGTH_SHORT).show();
                        // For now, keep placeholder or empty fields
                        greetingText.setText("Hi, User");
                        sectionText.setText("N/A");
                    }
                });
        } else {
            // User is not logged in, handle this case (e.g., redirect to login)
            Log.d(TAG, "loadPersonalDataFromFirestore: User not logged in.");
            Toast.makeText(this, "Please log in to view personal data.", Toast.LENGTH_SHORT).show();
            // Redirect to login or show an appropriate message
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void handleNavigation(String destination) {
        switch (destination) {
            case "home":
                startActivity(new Intent(this, HomeActivity.class));
                finish();
                break;
            case "schedule":
                startActivity(new Intent(this, ScheduleActivity.class));
                finish();
                break;
            case "personal_data":
                // Already on personal data
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