package com.ntc.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextInputLayout usernameLayout;
    private TextInputLayout passwordLayout;
    private TextInputEditText usernameInput;
    private TextInputEditText passwordInput;
    private MaterialButton loginButton;
    private View forgotPasswordText;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Set up auth state listener
        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.d(TAG, "onAuthStateChanged: signed_in: " + user.getUid());
            } else {
                // User is signed out
                Log.d(TAG, "onAuthStateChanged: signed_out");
            }
        };

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

    @Override
    protected void onStart() {
        super.onStart();
        // Add auth state listener
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Remove auth state listener
        if (authStateListener != null) {
            mAuth.removeAuthStateListener(authStateListener);
        }
    }

    private void handleLogin() {
        // Reset errors
        usernameLayout.setError(null);
        passwordLayout.setError(null);

        String email = usernameInput.getText() != null ? usernameInput.getText().toString() : "";
        String password = passwordInput.getText() != null ? passwordInput.getText().toString() : "";

        // Validate inputs
        boolean isValid = true;

        if (email.isEmpty()) {
            usernameLayout.setError("Please enter email");
            isValid = false;
        }

        if (password.isEmpty()) {
            passwordLayout.setError("Please enter password");
            isValid = false;
        }

        if (isValid) {
            // Show loading state
            loginButton.setEnabled(false);
            loginButton.setText("Logging in...");

            // Sign in with Firebase
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        
                        // Navigate to HomeActivity
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish(); // Close MainActivity so user can't go back
                    } else {
                        // If sign in fails, display a message to the user
                        Toast.makeText(MainActivity.this, "Authentication failed: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                        // Reset button state
                        loginButton.setEnabled(true);
                        loginButton.setText("Log In");
                    }
                });
        }
    }

    private void handleForgotPassword() {
        String email = usernameInput.getText() != null ? usernameInput.getText().toString() : "";
        
        if (email.isEmpty()) {
            usernameLayout.setError("Please enter your email to reset password");
            return;
        }

        mAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to send reset email: " + task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
    }
}