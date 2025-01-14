package com.example.burnaid;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class dashboard extends AppCompatActivity {
    private TextView circle, photosUploaded, problemsFound;
    private int photosCount = 0;
    private int problemsCount = 0;
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int CAMERA_PERMISSION_CODE = 101;
    private static final int GALLERY_REQUEST_CODE = 102;
    private Uri imageUri;
    private CardView infoCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the TextViews
        circle = findViewById(R.id.circle);
        photosUploaded = findViewById(R.id.photosUploaded);
        problemsFound = findViewById(R.id.problemsFound);

        // Initialize the UI with starting values
        setInitialValues();

        // Simulate photo upload (increment the photo count)
        photosCount++; // Increment the photo count after a photo is uploaded
        problemsCount = photosCount; // For simplicity, assume problems found equal photos uploaded

        // Update the UI with the new values
        updateUI();

        
    }

    // Set initial values for the UI
    private void setInitialValues() {
        circle.setText("0");  // Set initial circle text to 0
        photosUploaded.setText("Photos uploaded: 0");
        problemsFound.setText("Problems found: 0");
    }

    // Update the UI based on the updated values
    private void updateUI() {
        circle.setText(String.valueOf(photosCount)); // Update circle with photo count
        photosUploaded.setText("Photos uploaded: " + photosCount);
        problemsFound.setText("Problems found: " + problemsCount);

        Button reminderButton = findViewById(R.id.reminder_button);

// Button par click listener set karna
        reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Jab button click ho, notification activity open hogi
                Intent intent = new Intent(dashboard.this, notification.class);
                startActivity(intent); // notification activity ko launch karo
            }
        });
        // Initialize the CardView
        infoCard = findViewById(R.id.infoCard);

        // Set click listener on the CardView
        infoCard.setOnClickListener(v -> {
            // Navigate to the next page
            Intent intent = new Intent(dashboard.this, manual.class);
            startActivity(intent);
        });

        TextView sunburnCard = findViewById(R.id.sunburnCard);
        TextView kitchenCard = findViewById(R.id.kitchenCard);
        TextView fireburnCard = findViewById(R.id.fireburnCard);

        sunburnCard.setOnClickListener(v -> {
            Intent intent = new Intent(dashboard.this, blog1.class);
            startActivity(intent);
        });

        kitchenCard.setOnClickListener(v -> {
            Intent intent = new Intent(dashboard.this, blog2.class);
            startActivity(intent);
        });

        fireburnCard.setOnClickListener(v -> {
            Intent intent = new Intent(dashboard.this, blog3.class);
            startActivity(intent);
        });

        ImageButton homeButton = findViewById(R.id.home_button);
        ImageButton cameraButton = findViewById(R.id.camera_button);
        ImageButton menuButton = findViewById(R.id.Menu_button);

        // Home Button: Refresh the dashboard or go to home screen
        homeButton.setOnClickListener(v -> recreate());

        // Camera Button: Open Camera or Gallery chooser dialog
        cameraButton.setOnClickListener(v -> showImagePickerDialog());

        // Settings Button: Open the Settings activity
        menuButton.setOnClickListener(v -> {
            Intent settingsIntent = new Intent(dashboard.this, notification.class);
            startActivity(settingsIntent);
        });
    }

    // Method to show a chooser for Camera or Gallery
    private void showImagePickerDialog() {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Select Option")
                .setMessage("Choose an image from Camera or Gallery")
                .setPositiveButton("Camera", (dialog, which) -> {
                    if (ContextCompat.checkSelfPermission(dashboard.this, android.Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {
                        openCamera();
                    } else {
                        requestCameraPermission();
                    }
                })
                .setNegativeButton("Gallery", (dialog, which) -> openGallery())
                .create()
                .show();
    }

    // Method to open the camera
    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
    }

    // Method to open the gallery
    private void openGallery() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhotoIntent, GALLERY_REQUEST_CODE);
    }

    // Method to request camera permission
    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(dashboard.this, android.Manifest.permission.CAMERA)) {
            new android.app.AlertDialog.Builder(dashboard.this)
                    .setTitle("Camera Permission Needed")
                    .setMessage("This app needs the Camera permission to take photos.")
                    .setPositiveButton("OK", (dialog, which) -> ActivityCompat.requestPermissions(
                            dashboard.this,
                            new String[]{android.Manifest.permission.CAMERA},
                            CAMERA_PERMISSION_CODE
                    ))
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(dashboard.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }

    // Handling the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(dashboard.this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Handling the result of the camera or gallery intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                if (imageUri != null) {
                    // Simulate scanning process (process image here if needed)
                    processImage(imageUri);

                    // Increment photo count and update UI
                    photosCount++;
                    problemsCount = photosCount; // Assume problems found equal photos uploaded
                    updateUI();

                    Toast.makeText(this, "Image scanned and saved successfully.", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == GALLERY_REQUEST_CODE) {
                if (data != null && data.getData() != null) {
                    Uri selectedImageUri = data.getData();

                    // Simulate scanning process (process image here if needed)
                    processImage(selectedImageUri);

                    // Increment photo count and update UI
                    photosCount++;
                    problemsCount = photosCount; // Assume problems found equal photos uploaded
                    updateUI();

                    Toast.makeText(this, "Image selected and scanned: " + selectedImageUri.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // Simulate processing the image (you can add actual image processing logic here)
    private void processImage(Uri imageUri) {
        // Image processing logic (e.g., detecting burns, scanning, etc.)
    }
}