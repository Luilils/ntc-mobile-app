package com.ntc.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class ChangePasswordActivity extends AppCompatActivity {
    private TextInputEditText oldPasswordEditText, newPasswordEditText, confirmPasswordEditText;
    private ImageButton submitButton, resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPasswordEditText = findViewById(R.id.oldPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        submitButton = findViewById(R.id.submitButton);
        resetButton = findViewById(R.id.resetButton);

        submitButton.setOnClickListener(v -> handleChangePassword());
        resetButton.setOnClickListener(v -> handleResetPassword());

        setupNavigation();
    }

    private void handleResetPassword() {
        oldPasswordEditText.setText("");
        newPasswordEditText.setText("");
        confirmPasswordEditText.setText("");
        Toast.makeText(this, "Fields reset", Toast.LENGTH_SHORT).show();
    }

    private void handleChangePassword() {
        String oldPassword = oldPasswordEditText.getText() != null ? oldPasswordEditText.getText().toString() : "";
        String newPassword = newPasswordEditText.getText() != null ? newPasswordEditText.getText().toString() : "";
        String confirmPassword = confirmPasswordEditText.getText() != null ? confirmPasswordEditText.getText().toString() : "";

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
        // No API call, just show success
        Toast.makeText(this, "Password changed successfully!", Toast.LENGTH_LONG).show();
        oldPasswordEditText.setText("");
        newPasswordEditText.setText("");
        confirmPasswordEditText.setText("");
    }

    private void setupNavigation() {
        findViewById(R.id.nav_home).setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class)); finish();
        });
        findViewById(R.id.nav_schedule).setOnClickListener(v -> {
            startActivity(new Intent(this, ScheduleActivity.class)); finish();
        });
        findViewById(R.id.nav_personal_data).setOnClickListener(v -> {
            startActivity(new Intent(this, PersonalDataActivity.class)); finish();
        });
        findViewById(R.id.nav_student_eval).setOnClickListener(v -> {
            startActivity(new Intent(this, StudentEvaluationActivity.class)); finish();
        });
        findViewById(R.id.nav_teacher_eval).setOnClickListener(v -> {
            startActivity(new Intent(this, TeacherEvaluationActivity.class)); finish();
        });
        findViewById(R.id.nav_account_receivable).setOnClickListener(v -> {
            startActivity(new Intent(this, AccountReceivableActivity.class)); finish();
        });
        findViewById(R.id.nav_change_password).setOnClickListener(v -> {
            // Already here
        });
    }
} 