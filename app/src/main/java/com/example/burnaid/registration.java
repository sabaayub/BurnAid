package com.example.burnaid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class registration extends AppCompatActivity {
    private EditText fullName, email, password, confirmPassword;
    private ImageButton passwordToggle, confirmPasswordToggle;
    private CheckBox termsCheckbox, offersCheckbox;
    private Button registerButton;
    private boolean isPasswordVisible = false, isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Initialize UI elements
        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        passwordToggle = findViewById(R.id.password_toggle);
        confirmPasswordToggle = findViewById(R.id.confirm_password_toggle);
        termsCheckbox = findViewById(R.id.termsCheckbox);
        offersCheckbox = findViewById(R.id.offersCheckbox);
        registerButton = findViewById(R.id.reg_btn);



        // Toggle password visibility
        passwordToggle.setOnClickListener(view -> togglePasswordVisibility(password, passwordToggle));
        confirmPasswordToggle.setOnClickListener(view -> togglePasswordVisibility(confirmPassword, confirmPasswordToggle));

        // Register button click listener
        registerButton.setOnClickListener(view -> {
            String fullNameText = fullName.getText().toString().trim();
            String emailText = email.getText().toString().trim();
            String passwordText = password.getText().toString();
            String confirmPasswordText = confirmPassword.getText().toString();

            // Validate inputs
            if (TextUtils.isEmpty(fullNameText)) {
                fullName.setError("Full Name is required");
                return;
            }
            if (TextUtils.isEmpty(emailText) || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                email.setError("Valid Email is required");
                return;
            }
            if (TextUtils.isEmpty(passwordText) || passwordText.length() < 6) {
                password.setError("Password must be at least 6 characters");
                return;
            }
            if (!passwordText.equals(confirmPasswordText)) {
                confirmPassword.setError("Passwords do not match");
                return;
            }
            if (!termsCheckbox.isChecked()) {
                Toast.makeText(this, "You must accept the Terms and Conditions", Toast.LENGTH_SHORT).show();
                return;
            }

            // Registration successful
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();

            // Go to Dashboard
            Intent intent = new Intent(registration.this, dashboard.class);
            startActivity(intent);
            finish(); // Close registration activity
        });
    }

    private void togglePasswordVisibility(EditText editText, ImageButton toggleButton) {
        if (editText.getTransformationMethod() instanceof PasswordTransformationMethod) {
            // Show password
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            toggleButton.setImageResource(R.drawable.password); // Change to "eye" icon
        } else {
            // Hide password
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            toggleButton.setImageResource(R.drawable.hidden); // Change to "lock" icon
        }
        editText.setSelection(editText.getText().length()); // Keep cursor at the end
    }
}
