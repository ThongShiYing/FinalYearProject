package com.example.healthwithme.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.healthwithme.R;

public class HomePageActivity extends AppCompatActivity {
    TextView water,calories,exercise;
    ImageButton home,profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        water = findViewById(R.id.Water_text);
        calories = findViewById(R.id.calorie_text);
        exercise = findViewById(R.id.exercise_text);
        home = findViewById(R.id.Home);
        profile = findViewById(R.id.Profile);

        profile.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ProfilePageActivity.class)));

        water.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), DrinkWaterReminderPageActivity.class)));

        calories.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), CaloriesRecorderPageActivity.class)));

        exercise.setOnClickListener(v->startActivity(new Intent(getApplicationContext(), ExerciseReminderPageActivity.class)));


    }
}