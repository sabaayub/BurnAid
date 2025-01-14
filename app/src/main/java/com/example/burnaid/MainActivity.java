package com.example.burnaid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final int SPLASH_DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
            // Set the logo image
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageResource(R.drawable.logo);

            // Delay for SPLASH_DURATION, then start the main activity
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Move to the main activity
                    Intent intent = new Intent(MainActivity.this, Alert.class);
                    startActivity(intent);
                    finish(); // Close the splash activity
                }
            }, SPLASH_DURATION);


    }
}