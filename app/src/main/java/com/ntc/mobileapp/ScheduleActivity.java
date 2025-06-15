package com.ntc.mobileapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.util.TypedValue;
import android.view.Gravity;

public class ScheduleActivity extends AppCompatActivity {
    private ImageButton navHome, navSchedule, navPersonalData, navStudentEval,
            navTeacherEval, navAccountReceivable, navChangePassword;
    private TableLayout scheduleTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        initializeViews();
        setupNavigationListeners();
        createScheduleTable();
        loadScheduleFromDatabase();
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
        TableLayout timeColumn = findViewById(R.id.timeColumn);
        createTimeColumn(timeColumn);
        createScheduleTable();
        // Schedule items will be loaded from database
        loadScheduleFromDatabase();
    }

    private void createTimeColumn(TableLayout timeColumn) {
        // Create time slots for full day (7:00 AM to 12:00 AM)
        String[] times = {
            "7:00 AM",
            "8:00 AM",
            "9:00 AM",
            "10:00 AM",
            "11:00 AM",
            "12:00 PM",
            "1:00 PM",
            "2:00 PM",
            "3:00 PM",
            "4:00 PM",
            "5:00 PM",
            "6:00 PM",
            "7:00 PM",
            "8:00 PM",
            "9:00 PM",
            "10:00 PM",
            "11:00 PM",
            "12:00 AM"
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

    private void loadScheduleFromDatabase() {
        // TODO: Implement database loading
        // This method will:
        // 1. Query the database for user's schedule
        // 2. Create schedule items based on database data
        // 3. Add them to the schedule table
    }

    private void addScheduleItem(String title, String room, float startHour, float endHour, int dayColumn, String color) {

        // Calculate row index based on hour
        int startRow = (int)(startHour - 7); // Assuming schedule starts at 7 AM
        
        TableRow row = (TableRow) scheduleTable.getChildAt(startRow);
        if (row != null) {
            FrameLayout cell = (FrameLayout) row.getChildAt(dayColumn);
            if (cell != null) {
                CardView card = new CardView(this);
                card.setCardBackgroundColor(Color.parseColor(color));
                card.setRadius(getResources().getDimensionPixelSize(R.dimen.card_corner_radius));
                card.setCardElevation(getResources().getDimensionPixelSize(R.dimen.card_elevation));
                
                TextView content = new TextView(this);
                content.setText(title + "\n" + room);
                content.setTextColor(Color.WHITE);
                content.setTextSize(14);
                content.setPadding(16, 12, 16, 12);
                content.setGravity(android.view.Gravity.CENTER_VERTICAL);
                
                card.addView(content);
                
                // Calculate position and height based on start and end hours
                float duration = endHour - startHour;
                float minuteOffset = (startHour - (float)Math.floor(startHour)) * 
                    getResources().getDimensionPixelSize(R.dimen.time_slot_height);
                
                FrameLayout.LayoutParams cardParams = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    (int)(duration * getResources().getDimensionPixelSize(R.dimen.time_slot_height)));
                cardParams.setMargins(2, (int)minuteOffset, 8, 
                    getResources().getDimensionPixelSize(R.dimen.time_slot_margin));
                card.setLayoutParams(cardParams);
                
                cell.addView(card);
            }
        }
    }

    private void setupNavigationListeners() {
        navHome.setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });
        // Schedule button is current page
        navPersonalData.setOnClickListener(v -> {
            startActivity(new Intent(this, PersonalDataActivity.class));
            finish();
        });
        navStudentEval.setOnClickListener(v -> handleNavigation("student_eval"));
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
                // Already on schedule
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