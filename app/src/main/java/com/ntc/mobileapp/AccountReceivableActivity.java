package com.ntc.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.ntc.mobileapp.adapters.PaymentHistoryAdapter;
import com.ntc.mobileapp.models.PaymentEntry;
import java.util.ArrayList;
import java.util.List;

public class AccountReceivableActivity extends AppCompatActivity {
    private static final String TAG = "AccountReceivableActivity";
    private ImageButton navHome, navSchedule, navPersonalData, navStudentEval,
            navTeacherEval, navAccountReceivable, navChangePassword;
    private ImageView profileImageView;
    private TextView studentNameText;
    private TextView courseText;
    private TextView accountReceivableHeader;
    private RecyclerView paymentHistoryRecyclerView;
    private PaymentHistoryAdapter paymentHistoryAdapter;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_receivable);

        // Initialize Firebase instances
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

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
        if (currentUser == null) {
            Toast.makeText(this, "Please log in to view account information", Toast.LENGTH_SHORT).show();
            return;
        }

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
                    
                    profileImageView.setImageResource(R.drawable.profile_template);
                    studentNameText.setText(name != null ? name : "Name not set");
                    courseText.setText(course != null ? course : "Course not set");
                    
                    // Update header with user's name (Removed: reverted to static text)
                    accountReceivableHeader.setText("Account Receivable");
                } else {
                    Log.d(TAG, "No such document for current user UID: " + currentUser.getUid());
                    Toast.makeText(this, "User profile not found", Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(e -> {
                Log.e(TAG, "Error loading student info", e);
                Toast.makeText(this, "Error loading student information", Toast.LENGTH_SHORT).show();
            });
    }

    private void setupPaymentHistoryRecyclerView() {
        paymentHistoryAdapter = new PaymentHistoryAdapter();
        paymentHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        paymentHistoryRecyclerView.setAdapter(paymentHistoryAdapter);
    }

    private void loadPaymentHistory() {
        if (currentUser == null) {
            Toast.makeText(this, "Please log in to view payment history", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(TAG, "Current User UID: " + currentUser.getUid());
        Log.d(TAG, "Querying 'payments' collection for userId: " + currentUser.getUid());
        Log.d(TAG, "Ensure your Firestore 'payments' documents have a field named 'userId' (lowercase 'u', uppercase 'I') with this exact UID.");

        // Show loading state
        Toast.makeText(this, "Loading payment history...", Toast.LENGTH_SHORT).show();

        // Fetch payment history from Firestore
        db.collection("payments")
            .whereEqualTo("userId", currentUser.getUid())
            .orderBy("Date", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                List<PaymentEntry> payments = new ArrayList<>();
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    try {
                        PaymentEntry payment = document.toObject(PaymentEntry.class);
                        payments.add(payment);
                        Log.d(TAG, "Loaded payment: " + payment.getDescription());
                    } catch (Exception e) {
                        Log.e(TAG, "Error converting document to PaymentEntry", e);
                    }
                }
                
                if (payments.isEmpty()) {
                    Toast.makeText(this, "No payment records found", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Loaded " + payments.size() + " payment records", Toast.LENGTH_SHORT).show();
                }
                
                paymentHistoryAdapter.updatePayments(payments);
            })
            .addOnFailureListener(e -> {
                Log.e(TAG, "Error loading payment history", e);
                String errorMessage;
                if (e.getMessage() != null) {
                    if (e.getMessage().contains("PERMISSION_DENIED")) {
                        errorMessage = "Permission denied. Please check Firebase security rules.";
                    } else if (e.getMessage().contains("FAILED_PRECONDITION") && e.getMessage().contains("index")) {
                        errorMessage = "Please wait while we set up the database. This may take a few minutes.";
                    } else {
                        errorMessage = "Error loading payment history: " + e.getMessage();
                    }
                } else {
                    errorMessage = "Error loading payment history: Unknown error";
                }
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            });
    }
} 