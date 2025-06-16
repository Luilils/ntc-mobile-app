package com.ntc.mobileapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.util.TypedValue;
import android.view.Gravity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.ntc.mobileapp.models.ScheduleEntry;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ScheduleActivity extends AppCompatActivity {

    private static final String TAG = "ScheduleActivity";
    private ImageButton navHome, navSchedule, navPersonalData, navStudentEval,
            navTeacherEval, navAccountReceivable, navChangePassword;
    private TableLayout scheduleTable;
    private TableLayout timeColumnTable;

    // Header UI elements
    private TextView userGreeting;
    private TextView academicPeriod;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;

    // Current selected period (for filtering schedule)
    private String currentSemester = "1st Semester"; // Default
    private String currentSchoolYear = "2024-2025"; // Default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        // Initialize Firebase instances
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        initializeViews();
        setupNavigationListeners();
        loadStudentInfo(); // Load student name and course
        createScheduleTable();
        // loadScheduleFromDatabase() will be called after student info is loaded or date/semester changes
        loadScheduleForDay("Monday"); // Load default day
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

        // Schedule table
        scheduleTable = findViewById(R.id.scheduleTable);
        timeColumnTable = findViewById(R.id.timeColumn);
        createTimeColumn(timeColumnTable);
        // Schedule items will be loaded from database

        // Header UI elements
        userGreeting = findViewById(R.id.userGreeting);
        academicPeriod = findViewById(R.id.academicPeriod);
    }

    private void createTimeColumn(TableLayout timeColumn) {
        // Create time slots for full day (7:00 AM to 12:00 AM)
        String[] times = {
            "7:00 AM", "8:00 AM", "9:00 AM", "10:00 AM", "11:00 AM",
            "12:00 PM", "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM",
            "5:00 PM", "6:00 PM", "7:00 PM", "8:00 PM", "9:00 PM",
            "10:00 PM", "11:00 PM", "12:00 AM"
        };

        for (String time : times) {
            TableRow row = new TableRow(this);
            TextView timeSlot = new TextView(this);
            timeSlot.setText(time);
            timeSlot.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            timeSlot.setPadding(16, 0, 16, 0);
            timeSlot.setGravity(Gravity.CENTER_VERTICAL);
            
            TableRow.LayoutParams params = new TableRow.LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.time_column_width),
                getResources().getDimensionPixelSize(R.dimen.time_slot_height)
            );
            timeSlot.setLayoutParams(params);
            
            row.addView(timeSlot);
            timeColumn.addView(row);
        }
    }

    private void createScheduleTable() {
        // Create 5 columns (Monday to Friday) for each time slot
        for (int i = 0; i < 18; i++) { // 18 rows (7 AM to 12 AM)
            TableRow row = new TableRow(this);
            
            for (int j = 0; j < 5; j++) { // 5 columns (Mon-Fri)
                FrameLayout cell = new FrameLayout(this);
                TableRow.LayoutParams cellParams = new TableRow.LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen.schedule_column_width),
                    getResources().getDimensionPixelSize(R.dimen.time_slot_height)
                );
                cell.setLayoutParams(cellParams);
                
                row.addView(cell);
            }
            
            scheduleTable.addView(row);
        }
    }

    private void loadStudentInfo() {
        // Header UI elements
        userGreeting = findViewById(R.id.userGreeting);
        academicPeriod = findViewById(R.id.academicPeriod);

        if (currentUser == null) {
            userGreeting.setText("Hi, Guest");
            academicPeriod.setText("Please log in");
            Toast.makeText(this, "Please log in to view your schedule", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(TAG, "Current User ID: " + currentUser.getUid()); // Added for debugging

        // Fetch student information from Firestore
        db.collection("users")
            .document(currentUser.getUid())
            .get()
            .addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    String name = documentSnapshot.getString("name");
                    String course = documentSnapshot.getString("course");

                    Log.d(TAG, "Fetched User Name: " + (name != null ? name : "null"));
                    Log.d(TAG, "Fetched User Course: " + (course != null ? course : "null"));

                    userGreeting.setText("Hi, " + (name != null ? name : "User"));
                    academicPeriod.setText("Course: " + (course != null ? course : "Not Set"));
                } else {
                    Log.d(TAG, "No such user document for UID: " + currentUser.getUid());
                    userGreeting.setText("Hi, User");
                    academicPeriod.setText("Course: N/A");
                    Toast.makeText(this, "User profile not found", Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(e -> {
                Log.e(TAG, "Error loading student info", e);
                userGreeting.setText("Hi, User");
                academicPeriod.setText("Course: Error");
                Toast.makeText(this, "Error loading student information", Toast.LENGTH_SHORT).show();
            });
    }

    private void loadScheduleForDay(String day) {
        if (currentUser == null) {
            Log.d(TAG, "Not logged in, cannot load schedule.");
            return;
        }

        // Clear existing schedule items from the table
        clearScheduleTable();

        // Query Firestore for schedule entries
        db.collection("schedules")
            .whereEqualTo("userId", currentUser.getUid())
            .whereEqualTo("semester", currentSemester)
            .whereEqualTo("schoolYear", currentSchoolYear)
            .orderBy("startTime", com.google.firebase.firestore.Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                Log.d(TAG, "Fetched " + queryDocumentSnapshots.size() + " total schedule entries for client-side filtering.");
                List<ScheduleEntry> entriesForDay = new ArrayList<>();
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    try {
                        ScheduleEntry entry = document.toObject(ScheduleEntry.class);
                        // Client-side filtering: check if the entry's dayofWeek string contains the current day
                        if (entry.getDayofWeek() != null && entry.getDayofWeek().toLowerCase(Locale.getDefault()).contains(day.toLowerCase(Locale.getDefault()))) {
                            Log.d(TAG, "Adding schedule item: " + entry.getSubjectName() + " at " + entry.getStartTime() + " on " + entry.getDayofWeek());
                            entriesForDay.add(entry);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error converting document to ScheduleEntry", e);
                        Toast.makeText(this, "Error processing schedule entry", Toast.LENGTH_SHORT).show();
                    }
                }

                if (entriesForDay.isEmpty()) {
                    Toast.makeText(this, "No schedule found for " + day, Toast.LENGTH_SHORT).show();
                } else {
                    // Add filtered entries to the schedule table
                    for (ScheduleEntry entry : entriesForDay) {
                        addScheduleItem(entry);
                    }
                }
            })
            .addOnFailureListener(e -> {
                Log.e(TAG, "Error loading schedule for " + day, e);
                Toast.makeText(this, "Error loading schedule: " + e.getMessage(), Toast.LENGTH_LONG).show();
            });
    }

    private void clearScheduleTable() {
        for (int i = 0; i < scheduleTable.getChildCount(); i++) {
            TableRow row = (TableRow) scheduleTable.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                FrameLayout cell = (FrameLayout) row.getChildAt(j);
                cell.removeAllViews(); // Remove all dynamic views (subjects)
            }
        }
    }

    private void addScheduleItem(ScheduleEntry entry) {
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            Date startTime = timeFormat.parse(entry.getStartTime());
            Date endTime = timeFormat.parse(entry.getEndTime());

            Calendar startCal = Calendar.getInstance();
            startCal.setTime(startTime);
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(endTime);

            // Calculate row index based on hour (7 AM is row 0)
            int startHour = startCal.get(Calendar.HOUR_OF_DAY);
            float startMinute = startCal.get(Calendar.MINUTE) / 60.0f;
            int endHour = endCal.get(Calendar.HOUR_OF_DAY);
            float endMinute = endCal.get(Calendar.MINUTE) / 60.0f;

            // Adjust for 12 AM (midnight) case if it's the end of the day
            if (endHour == 0 && endCal.get(Calendar.AM_PM) == Calendar.AM) { // 12 AM
                endHour = 24; // Treat as 24th hour for duration calculation
            }
            if (startHour == 0 && startCal.get(Calendar.AM_PM) == Calendar.AM) { // 12 AM
                startHour = 24; // Treat as 24th hour for duration calculation
            }

            int startRow = (int) (startHour + startMinute - 7); // Assuming schedule starts at 7 AM (row 0)
            float durationHours = (endHour + endMinute) - (startHour + startMinute);

            // Determine day column
            int dayColumn = -1;
            switch (entry.getDayofWeek().toLowerCase(Locale.getDefault())) {
                case "monday": dayColumn = 0; break;
                case "tuesday": dayColumn = 1; break;
                case "wednesday": dayColumn = 2; break;
                case "thursday": dayColumn = 3; break;
                case "friday": dayColumn = 4; break;
                // Add more days if needed
            }

            if (dayColumn != -1) {
                TableRow row = (TableRow) scheduleTable.getChildAt(startRow);
                if (row != null) {
                    FrameLayout cell = (FrameLayout) row.getChildAt(dayColumn);
                    if (cell != null) {
                        CardView card = new CardView(this);
                        try {
                            card.setCardBackgroundColor(Color.parseColor(entry.getColorHex()));
                        } catch (IllegalArgumentException e) {
                            Log.e(TAG, "Invalid color hex: " + entry.getColorHex(), e);
                            card.setCardBackgroundColor(Color.GRAY); // Default to gray on error
                        }
                        card.setRadius(getResources().getDimensionPixelSize(R.dimen.card_corner_radius));
                        card.setCardElevation(getResources().getDimensionPixelSize(R.dimen.card_elevation));

                        TextView content = new TextView(this);
                        content.setText(entry.getSubjectCode() + "\n" + entry.getRoom() + "\n" + entry.getInstructor());
                        content.setTextColor(Color.WHITE);
                        content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12); // Smaller text size
                        content.setPadding(8, 8, 8, 8);
                        content.setGravity(android.view.Gravity.CENTER); // Center text within card

                        card.addView(content);

                        // Calculate position and height based on start and end hours
                        float minuteOffsetPx = startMinute * getResources().getDimensionPixelSize(R.dimen.time_slot_height);
                        int heightPx = (int) (durationHours * getResources().getDimensionPixelSize(R.dimen.time_slot_height));

                        FrameLayout.LayoutParams cardParams = new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            heightPx);
                        cardParams.setMargins(2, (int)minuteOffsetPx, 2, 0); // Adjust margins as needed
                        card.setLayoutParams(cardParams);

                        cell.addView(card);
                    }
                }
            }
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing time: " + entry.getStartTime() + " or " + entry.getEndTime(), e);
            Toast.makeText(this, "Error parsing schedule time format", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Error adding schedule item", e);
            Toast.makeText(this, "Error displaying schedule item", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupNavigationListeners() {
        navHome.setOnClickListener(v -> handleNavigation("home"));
        // navSchedule is the current page
        navPersonalData.setOnClickListener(v -> handleNavigation("personal_data"));
        navStudentEval.setOnClickListener(v -> handleNavigation("student_eval"));
        navTeacherEval.setOnClickListener(v -> handleNavigation("teacher_eval"));
        navAccountReceivable.setOnClickListener(v -> handleNavigation("account_receivable"));
        navChangePassword.setOnClickListener(v -> handleNavigation("change_password"));
    }

    private void handleNavigation(String destination) {
        Intent intent = null;
        switch (destination) {
            case "home":
                intent = new Intent(this, HomeActivity.class);
                break;
            case "schedule":
                // Already on schedule
                break;
            case "personal_data":
                intent = new Intent(this, PersonalDataActivity.class);
                break;
            case "student_eval":
                intent = new Intent(this, StudentEvaluationActivity.class);
                break;
            case "teacher_eval":
                intent = new Intent(this, TeacherEvaluationActivity.class);
                break;
            case "account_receivable":
                intent = new Intent(this, AccountReceivableActivity.class);
                break;
            case "change_password":
                intent = new Intent(this, ChangePasswordActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            finish();
        }
    }
} 