package com.ntc.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ntc.mobileapp.adapters.CourseAdapter;
import com.ntc.mobileapp.models.CourseEntry;
import com.ntc.mobileapp.dialogs.TeacherEvaluationFormDialog;
import com.ntc.mobileapp.dialogs.AcceptFormDialog;
import com.ntc.mobileapp.dialogs.TeacherEvaluationFormDialog.TeacherEvaluationListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TeacherEvaluationActivity extends AppCompatActivity
        implements CourseAdapter.OnInstructorClickListener,
        TeacherEvaluationFormDialog.TeacherEvaluationListener {

    private static final String TAG = "TeacherEvaluationActivity";
    private ImageButton navHome, navSchedule, navPersonalData, navStudentEval,
            navTeacherEval, navAccountReceivable, navChangePassword;
    private ImageView profileImageView;
    private TextView studentNameText;
    private TextView courseText;
    private TextView teacherEvaluationHeader;

    // New declarations for teacher evaluation content
    private AutoCompleteTextView yearSpinner;
    private MaterialButtonToggleGroup semesterToggleGroup;
    private MaterialButton firstSemButton, secondSemButton;
    private RecyclerView courseRecyclerView;
    private CourseAdapter courseAdapter;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_evaluation);

        // Initialize Firebase instances
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        initializeViews();
        setupNavigationListeners();
        loadStudentInfo();
        setupYearSpinner();
        setupSemesterToggleGroup();
        setupCourseRecyclerView();

        // Set the listener for the CourseAdapter
        courseAdapter.setOnInstructorClickListener(this);

        // Load initial course data
        loadCourseData();
    }

    private void initializeViews() {
        navHome = findViewById(R.id.nav_home);
        navSchedule = findViewById(R.id.nav_schedule);
        navPersonalData = findViewById(R.id.nav_personal_data);
        navStudentEval = findViewById(R.id.nav_student_eval);
        navTeacherEval = findViewById(R.id.nav_teacher_eval);
        navAccountReceivable = findViewById(R.id.nav_account_receivable);
        navChangePassword = findViewById(R.id.nav_change_password);

        profileImageView = findViewById(R.id.profileImageView);
        studentNameText = findViewById(R.id.studentNameText);
        courseText = findViewById(R.id.courseText);
        teacherEvaluationHeader = findViewById(R.id.teacherEvaluationHeader);

        // Initialize new views for teacher evaluation content
        yearSpinner = findViewById(R.id.yearSpinner);
        semesterToggleGroup = findViewById(R.id.semesterToggleGroup);
        firstSemButton = findViewById(R.id.firstSemButton);
        secondSemButton = findViewById(R.id.secondSemButton);
        courseRecyclerView = findViewById(R.id.courseRecyclerView);
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
            startActivity(new Intent(this, PersonalDataActivity.class));
            finish();
        });
        navStudentEval.setOnClickListener(v -> {
            startActivity(new Intent(this, StudentEvaluationActivity.class));
            finish();
        });
        navTeacherEval.setOnClickListener(v -> {
            // Already on teacher evaluation
        });
        navAccountReceivable.setOnClickListener(v -> handleNavigation("account_receivable"));
        navChangePassword.setOnClickListener(v -> handleNavigation("change_password"));
    }

    private void handleNavigation(String destination) {
        switch (destination) {
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

    private void loadStudentInfo() {
        if (currentUser == null) {
            Toast.makeText(this, "Please log in to view student information", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("users").document(currentUser.getUid())
            .get()
            .addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    String name = documentSnapshot.getString("name");
                    String course = documentSnapshot.getString("course");

                    Log.d(TAG, "Fetched User Name: " + (name != null ? name : "null"));
                    Log.d(TAG, "Fetched User Course: " + (course != null ? course : "null"));

                    profileImageView.setImageResource(R.drawable.profile_template);
                    studentNameText.setText(name != null ? name : "Name not set");
                    courseText.setText(course != null ? course : "Course not set");
                } else {
                    Log.d(TAG, "No such user document for UID: " + currentUser.getUid());
                    Toast.makeText(this, "User profile not found", Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(e -> {
                Log.e(TAG, "Error loading student info", e);
                Toast.makeText(this, "Error loading student information", Toast.LENGTH_SHORT).show();
            });
    }

    private void setupYearSpinner() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<String> years = new ArrayList<>();
        for (int i = 0; i < 5; i++) { // Last 5 years
            years.add(String.valueOf(currentYear - i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, R.layout.item_dropdown, years);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setText(years.get(0), false); // Set current year as default

        yearSpinner.setOnItemClickListener((parent, view, position, id) -> loadCourseData());
    }

    private void setupSemesterToggleGroup() {
        // Set initial selected button
        firstSemButton.setChecked(true);

        semesterToggleGroup.addOnButtonCheckedListener((toggleGroup, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.firstSemButton) {
                    firstSemButton.setTextColor(ContextCompat.getColor(this, R.color.white));
                    firstSemButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.primary_purple));
                    secondSemButton.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));
                    secondSemButton.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.transparent));
                } else if (checkedId == R.id.secondSemButton) {
                    secondSemButton.setTextColor(ContextCompat.getColor(this, R.color.white));
                    secondSemButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.primary_purple));
                    firstSemButton.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));
                    firstSemButton.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.transparent));
                }
                loadCourseData();
            }
        });
    }

    private void setupCourseRecyclerView() {
        courseAdapter = new CourseAdapter();
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseRecyclerView.setAdapter(courseAdapter);
    }

    private void loadCourseData() {
        // TODO: Load actual course data based on selected year and semester
        List<CourseEntry> courses = new ArrayList<>();

        // Sample data for 1st Semester
        if (firstSemButton.isChecked()) {
            courses.add(new CourseEntry("CC101", "Introduction to Computing w/LAB", "Mr. Luisito Rulona"));
            courses.add(new CourseEntry("CC102", "Computer Programming 1 w/LAB", "Mr. Kristian Carlo Rosales"));
            courses.add(new CourseEntry("GE1", "Understanding the Self", "Ms. Emelibe Meneses"));
            courses.add(new CourseEntry("GE2", "Art Appreciation", "Mr. Kristian Carlo Rosales"));
            courses.add(new CourseEntry("INFDT1", "College Logic and Math", "Mr. Lebron James"));
            courses.add(new CourseEntry("INFDT2", "Academic Communication", "Mr. Dwight Ramos"));
            courses.add(new CourseEntry("NSTP1", "Literacy Training Service 1", "Mr. Ricci Rivero"));
            courses.add(new CourseEntry("PC1", "Discrete Mathematics", "Mr. Emman Bacosa"));
        } else { // Sample data for 2nd Semester (or other semesters)
            courses.add(new CourseEntry("CC103", "Data Structures and Algorithms", "Mr. Stephen Curry"));
            courses.add(new CourseEntry("CC104", "Object-Oriented Programming", "Mr. Kevin Durant"));
            courses.add(new CourseEntry("GE3", "Ethics", "Ms. Klay Thompson"));
        }

        courseAdapter.updateCourses(courses);
    }

    @Override
    public void onInstructorClick(String instructorName) {
        // Show the teacher evaluation form dialog
        TeacherEvaluationFormDialog dialog = TeacherEvaluationFormDialog.newInstance(instructorName);
        dialog.setTargetFragment(null, 0); // Clear target fragment to avoid issues with dialog lifecycle
        dialog.show(getSupportFragmentManager(), "TeacherEvaluationFormDialog");
    }

    @Override
    public void onSubmitEvaluation(String instructorName, int[] ratings, String comment) {
        // Handle the submitted evaluation data (e.g., send to server, save locally)
        Toast.makeText(this, "Evaluation submitted for " + instructorName, Toast.LENGTH_SHORT).show();
        // Optionally, show the AcceptFormDialog
        AcceptFormDialog acceptDialog = new AcceptFormDialog();
        acceptDialog.show(getSupportFragmentManager(), "AcceptFormDialog");

        // You can add your logic here to save the evaluation data to Firestore
        // For example:
        // Map<String, Object> evaluation = new HashMap<>();
        // evaluation.put("instructorName", instructorName);
        // evaluation.put("ratings", Arrays.toString(ratings));
        // evaluation.put("comment", comment);
        // evaluation.put("timestamp", FieldValue.serverTimestamp());
        // db.collection("evaluations").add(evaluation);

        // Refresh the course list if needed
        loadCourseData();
    }
} 