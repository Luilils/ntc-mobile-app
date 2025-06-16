package com.ntc.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {
    private static final String TAG = "ChangePasswordActivity";
    private TextInputEditText oldPasswordEditText;
    private TextInputEditText newPasswordEditText;
    private TextInputEditText confirmPasswordEditText;
    private ImageButton submitButton;
    private ImageButton resetButton;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Initialize views
        oldPasswordEditText = findViewById(R.id.oldPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        submitButton = findViewById(R.id.submitButton);
        resetButton = findViewById(R.id.resetButton);

        // Set up click listeners
        submitButton.setOnClickListener(v -> handlePasswordChange());
        resetButton.setOnClickListener(v -> resetFields());

        // Set up bottom navigation
        setupBottomNavigation();
    }

    private void handlePasswordChange() {
        String oldPassword = oldPasswordEditText.getText().toString().trim();
        String newPassword = newPasswordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "New passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPassword.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get user's email
        String email = currentUser.getEmail();
        if (email == null) {
            Toast.makeText(this, "Error: User email not found", Toast.LENGTH_SHORT).show();
            return;
        }

        // Reauthenticate user before changing password
        AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);
        currentUser.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Update password
                        currentUser.updatePassword(newPassword)
                                .addOnCompleteListener(updateTask -> {
                                    if (updateTask.isSuccessful()) {
                                        Log.d(TAG, "Password updated successfully");
                                        Toast.makeText(ChangePasswordActivity.this, 
                                            "Password updated successfully", Toast.LENGTH_SHORT).show();
                                        
                                        // Sign out the user
                                        mAuth.signOut();
                                        
                                        // Wait for 1.5 seconds to show the success message
                                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                            // Redirect to login page
                                            Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();
                                        }, 1500);
                                    } else {
                                        Log.e(TAG, "Error updating password", updateTask.getException());
                                        Toast.makeText(ChangePasswordActivity.this,
                                            "Error updating password: " + updateTask.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Log.e(TAG, "Error reauthenticating", task.getException());
                        Toast.makeText(ChangePasswordActivity.this,
                            "Current password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void resetFields() {
        oldPasswordEditText.setText("");
        newPasswordEditText.setText("");
        confirmPasswordEditText.setText("");
    }

    private void setupBottomNavigation() {
        ImageButton navHome = findViewById(R.id.nav_home);
        ImageButton navSchedule = findViewById(R.id.nav_schedule);
        ImageButton navPersonalData = findViewById(R.id.nav_personal_data);
        ImageButton navStudentEval = findViewById(R.id.nav_student_eval);
        ImageButton navTeacherEval = findViewById(R.id.nav_teacher_eval);
        ImageButton navAccountReceivable = findViewById(R.id.nav_account_receivable);
        ImageButton navChangePassword = findViewById(R.id.nav_change_password);

        // Set up click listeners for navigation
        navHome.setOnClickListener(v -> startActivity(new Intent(this, HomeActivity.class)));
        navSchedule.setOnClickListener(v -> startActivity(new Intent(this, ScheduleActivity.class)));
        navPersonalData.setOnClickListener(v -> startActivity(new Intent(this, PersonalDataActivity.class)));
        navStudentEval.setOnClickListener(v -> startActivity(new Intent(this, StudentEvaluationActivity.class)));
        navTeacherEval.setOnClickListener(v -> startActivity(new Intent(this, TeacherEvaluationActivity.class)));
        navAccountReceivable.setOnClickListener(v -> startActivity(new Intent(this, AccountReceivableActivity.class)));
        navChangePassword.setOnClickListener(v -> startActivity(new Intent(this, ChangePasswordActivity.class)));
    }
} 