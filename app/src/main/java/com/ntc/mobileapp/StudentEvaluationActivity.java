package com.ntc.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.ntc.mobileapp.adapters.GradesAdapter;
import com.ntc.mobileapp.models.GradeEntry;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StudentEvaluationActivity extends AppCompatActivity {
    private static final String TAG = "StudentEvaluationActivity";
    private GradesAdapter gradesAdapter;
    private AutoCompleteTextView semesterSpinner;
    private AutoCompleteTextView schoolYearSpinner;
    private TextView studentNameText;
    private TextView courseText;
    private TextView totalUnitsText;
    private TextView gwaText;
    private SwipeRefreshLayout swipeRefresh;
    private View loadingState;
    private View errorState;
    private RecyclerView contentState;
    private MaterialButton retryButton;
    private MaterialToolbar toolbar;
    private ImageButton navHome, navSchedule, navPersonalData, navStudentEval,
            navTeacherEval, navAccountReceivable, navChangePassword;
    
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_evaluation);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize views
        initializeViews();
        setupToolbar();
        setupSpinners();
        setupRecyclerView();
        setupSwipeRefresh();
        setupErrorState();
        setupNavigationListeners();
        
        // Load initial data
        loadData();
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        semesterSpinner = findViewById(R.id.semesterSpinner);
        schoolYearSpinner = findViewById(R.id.schoolYearSpinner);
        studentNameText = findViewById(R.id.studentNameText);
        courseText = findViewById(R.id.courseText);
        totalUnitsText = findViewById(R.id.totalUnitsText);
        gwaText = findViewById(R.id.gwaText);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        loadingState = findViewById(R.id.loadingState);
        errorState = findViewById(R.id.errorState);
        retryButton = findViewById(R.id.retryButton);
        contentState = findViewById(R.id.gradesRecyclerView);

        // Initialize navigation buttons
        navHome = findViewById(R.id.nav_home);
        navSchedule = findViewById(R.id.nav_schedule);
        navPersonalData = findViewById(R.id.nav_personal_data);
        navStudentEval = findViewById(R.id.nav_student_eval);
        navTeacherEval = findViewById(R.id.nav_teacher_eval);
        navAccountReceivable = findViewById(R.id.nav_account_receivable);
        navChangePassword = findViewById(R.id.nav_change_password);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        // toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupSpinners() {
        // Setup semester spinner
        String[] semesters = {"1st Semester", "2nd Semester", "Summer"};
        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<>(this, R.layout.item_dropdown, semesters);
        semesterSpinner.setAdapter(semesterAdapter);

        // Setup school year spinner
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<String> schoolYears = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            schoolYears.add((currentYear - i) + "-" + (currentYear - i + 1));
        }
        ArrayAdapter<String> schoolYearAdapter = new ArrayAdapter<>(this, R.layout.item_dropdown, schoolYears);
        schoolYearSpinner.setAdapter(schoolYearAdapter);

        // Add listeners
        semesterSpinner.setOnItemClickListener((parent, view, position, id) -> loadData());
        schoolYearSpinner.setOnItemClickListener((parent, view, position, id) -> loadData());
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.gradesRecyclerView);
        gradesAdapter = new GradesAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(gradesAdapter);
    }

    private void setupSwipeRefresh() {
        swipeRefresh.setColorSchemeColors(
            ContextCompat.getColor(this, R.color.purple_500)
        );
        swipeRefresh.setOnRefreshListener(this::loadData);
    }

    private void setupErrorState() {
        retryButton.setOnClickListener(v -> loadData());
    }

    private void loadData() {
        // Show loading state
        showLoadingState();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Please log in to view grades.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        String userId = currentUser.getUid();
        String semester = semesterSpinner.getText().toString();
        String schoolYear = schoolYearSpinner.getText().toString();

        // Load student info
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    String fullName = documentSnapshot.getString("fullName");
                    String course = documentSnapshot.getString("course");
                    String yearLevel = documentSnapshot.getString("yearLevel");
                    
                    studentNameText.setText(fullName);
                    courseText.setText(course + " - " + yearLevel);
                }
            })
            .addOnFailureListener(e -> {
                Log.e(TAG, "Error loading student info", e);
                Toast.makeText(this, "Error loading student info", Toast.LENGTH_SHORT).show();
            });

        // Load grades
        db.collection("grades")
            .whereEqualTo("userId", userId)
            .whereEqualTo("semester", semester)
            .whereEqualTo("schoolYear", schoolYear)
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                List<GradeEntry> grades = new ArrayList<>();
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    GradeEntry grade = new GradeEntry(
                        document.getString("subjectCode"),
                        document.getString("subjectName"),
                        document.getLong("units").intValue(),
                        document.getDouble("grade").floatValue(),
                        document.getString("remarks")
                    );
                    grades.add(grade);
                }
                
                if (grades.isEmpty()) {
                    Toast.makeText(this, "No grades found for the selected period", Toast.LENGTH_SHORT).show();
                }
                
                gradesAdapter.updateGrades(grades);
                updateSummary(grades);
                showContentState();
            })
            .addOnFailureListener(e -> {
                Log.e(TAG, "Error loading grades", e);
                showErrorState();
                Toast.makeText(this, "Error loading grades", Toast.LENGTH_SHORT).show();
            });
    }

    private void updateSummary(List<GradeEntry> grades) {
        int totalUnits = 0;
        float weightedSum = 0;
        for (GradeEntry grade : grades) {
            // Only include grades with numerical remarks (e.g., Passed, F, Failed) in GWA calculation
            String remarks = grade.getRemarks();
            if ("Passed".equalsIgnoreCase(remarks) || "F".equalsIgnoreCase(remarks) || "Failed".equalsIgnoreCase(remarks)) {
                totalUnits += grade.getUnits();
                weightedSum += grade.getUnits() * grade.getGrade();
            }
        }
        float gwa = totalUnits > 0 ? weightedSum / totalUnits : 0.0f;

        totalUnitsText.setText(String.valueOf(totalUnits));
        gwaText.setText(String.format("%.2f", gwa));
    }

    private void setupNavigationListeners() {
        navHome.setOnClickListener(v -> handleNavigation("home"));
        navSchedule.setOnClickListener(v -> handleNavigation("schedule"));
        navPersonalData.setOnClickListener(v -> handleNavigation("personal_data"));
        navStudentEval.setOnClickListener(v -> {
            // Already on student evaluation
        });
        navTeacherEval.setOnClickListener(v -> handleNavigation("teacher_eval"));
        navAccountReceivable.setOnClickListener(v -> handleNavigation("account_receivable"));
        navChangePassword.setOnClickListener(v -> handleNavigation("change_password"));
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
                startActivity(new Intent(this, PersonalDataActivity.class));
                finish();
                break;
            case "student_eval":
                // Already on student evaluation
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

    private void showLoadingState() {
        loadingState.setVisibility(View.VISIBLE);
        errorState.setVisibility(View.GONE);
        contentState.setVisibility(View.GONE);
        swipeRefresh.setRefreshing(false);
    }

    private void showContentState() {
        loadingState.setVisibility(View.GONE);
        errorState.setVisibility(View.GONE);
        contentState.setVisibility(View.VISIBLE);
        swipeRefresh.setRefreshing(false);
    }

    private void showErrorState() {
        loadingState.setVisibility(View.GONE);
        errorState.setVisibility(View.VISIBLE);
        contentState.setVisibility(View.GONE);
        swipeRefresh.setRefreshing(false);
    }
} 