package com.example.healthwithme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class StretchingExercisePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stretching_exercise_page);

        Button startexercise_stretch = findViewById(R.id.startexercise_stretch);

        startexercise_stretch.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), StartStretchingExercisePageActivity.class)));
    }
}