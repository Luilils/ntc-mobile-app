package com.ntc.mobileapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout usernameLayout;
    private TextInputLayout passwordLayout;
    private TextInputEditText usernameInput;
    private TextInputEditText passwordInput;
    private MaterialButton loginButton;
    private View forgotPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        usernameLayout = findViewById(R.id.usernameLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        forgotPasswordText = findViewById(R.id.forgotPasswordText);

        // Set up click listeners
        loginButton.setOnClickListener(v -> handleLogin());
        forgotPasswordText.setOnClickListener(v -> handleForgotPassword());
    }

    private void handleLogin() {
        // Reset errors
        usernameLayout.setError(null);
        passwordLayout.setError(null);

        String username = usernameInput.getText() != null ? usernameInput.getText().toString() : "";
        String password = passwordInput.getText() != null ? passwordInput.getText().toString() : "";

        // Validate inputs
        boolean isValid = true;

        if (username.isEmpty()) {
            usernameLayout.setError("Please enter username or school ID");
            isValid = false;
        }

        if (password.isEmpty()) {
            passwordLayout.setError("Please enter password");
            isValid = false;
        }

        if (isValid) {
            // TODO: Implement actual login logic here
            Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleForgotPassword() {
        // TODO: Implement forgot password logic
        Toast.makeText(this, "Forgot password clicked", Toast.LENGTH_SHORT).show();
    }
}