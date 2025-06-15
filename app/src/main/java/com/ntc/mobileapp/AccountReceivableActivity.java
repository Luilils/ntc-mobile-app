package com.ntc.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ntc.mobileapp.adapters.PaymentHistoryAdapter;
import com.ntc.mobileapp.models.PaymentEntry;
import java.util.ArrayList;
import java.util.List;

public class AccountReceivableActivity extends AppCompatActivity {

    private ImageButton navHome, navSchedule, navPersonalData, navStudentEval,
            navTeacherEval, navAccountReceivable, navChangePassword;
    private ImageView profileImageView;
    private TextView studentNameText;
    private TextView courseText;
    private TextView accountReceivableHeader;
    private RecyclerView paymentHistoryRecyclerView;
    private PaymentHistoryAdapter paymentHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_receivable);

        initializeViews();
        setupNavigationListeners();
        loadStudentInfo();
        setupPaymentHistoryRecyclerView();
        loadPaymentHistory();
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
        accountReceivableHeader = findViewById(R.id.accountReceivableHeader);
        paymentHistoryRecyclerView = findViewById(R.id.paymentHistoryRecyclerView);
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
            startActivity(new Intent(this, TeacherEvaluationActivity.class));
            finish();
        });
        navAccountReceivable.setOnClickListener(v -> {
            // Already on account receivable
        });
        navChangePassword.setOnClickListener(v -> {
            startActivity(new Intent(this, ChangePasswordActivity.class));
            finish();
        });
    }

    private void loadStudentInfo() {
        profileImageView.setImageResource(R.drawable.profile_template);
        studentNameText.setText("Israel Andy T. Maliwat");
        courseText.setText("BACHELOR OF SCIENCE IN INFORMATION TECHNOLOGY");
    }

    private void setupPaymentHistoryRecyclerView() {
        paymentHistoryAdapter = new PaymentHistoryAdapter();
        paymentHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        paymentHistoryRecyclerView.setAdapter(paymentHistoryAdapter);
    }

    private void loadPaymentHistory() {
        // TODO: Load actual payment history from server
        List<PaymentEntry> payments = new ArrayList<>();
        
        // Sample data
        payments.add(new PaymentEntry(
            "2024-03-15",
            "₱15,000.00",
            "Tuition Fee - 1st Semester",
            "Paid"
        ));
        payments.add(new PaymentEntry(
            "2024-02-15",
            "₱5,000.00",
            "Miscellaneous Fee",
            "Paid"
        ));
        payments.add(new PaymentEntry(
            "2024-01-15",
            "₱10,000.00",
            "Tuition Fee - 1st Semester",
            "Paid"
        ));
        payments.add(new PaymentEntry(
            "2024-04-15",
            "₱15,000.00",
            "Tuition Fee - 2nd Semester",
            "Pending"
        ));

        paymentHistoryAdapter.updatePayments(payments);
    }
} 