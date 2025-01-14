package com.example.burnaid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class notification extends AppCompatActivity {
    private EditText nameInput, descriptionInput, dateInput;
    private Button setupButton;
    private ImageView closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize views
        nameInput = findViewById(R.id.name_input);
        descriptionInput = findViewById(R.id.description_input);
        dateInput = findViewById(R.id.date_input);
        setupButton = findViewById(R.id.setup_button);
        closeButton = findViewById(R.id.close_button);
// Set up button click listener
        setupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve user inputs
                String name = nameInput.getText().toString().trim();
                String description = descriptionInput.getText().toString().trim();
                String date = dateInput.getText().toString().trim();

                // Validate inputs
                if (name.isEmpty() || description.isEmpty() || date.isEmpty()) {
                    Toast.makeText(notification.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                } else {
                    // Simulate saving the notification (or implement actual functionality)
                    Toast.makeText(notification.this, "Notification set for: " + name, Toast.LENGTH_SHORT).show();

                    // Optionally clear fields after setup
                    nameInput.setText("");
                    descriptionInput.setText("");
                    dateInput.setText("");
                }
            }
        });

        // Close button click listener
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the activity
                finish();
            }
        });
    }
}