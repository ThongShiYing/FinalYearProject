package com.example.healthwithme.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.healthwithme.R;
import com.example.healthwithme.StartTabataExercisePageActivity;

public class TabataExercisePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabata_exercise_page);

        Button startexercise = findViewById(R.id.startexercise);

        startexercise.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), StartTabataExercisePageActivity.class)));
    }
}