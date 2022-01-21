package com.example.healthwithme.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.healthwithme.R;
import com.example.healthwithme.StretchingExercisePageActivity;

public class ExerciseReminderPageActivity extends AppCompatActivity {
    ImageButton imgBtnHomeExercise, imgBtnProfileExercise,imgBtnTabata,imgBtnStretching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_reminder_page);

        imgBtnHomeExercise = findViewById(R.id.home_exercise);
        imgBtnProfileExercise = findViewById(R.id.profile_exercise);
        imgBtnTabata = findViewById(R.id.tabata);
        imgBtnStretching = findViewById(R.id.stretching);

        imgBtnHomeExercise.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), HomePageActivity.class)));

        imgBtnProfileExercise.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ProfilePageActivity.class)));

        imgBtnTabata.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), TabataExercisePageActivity.class)));

        imgBtnStretching.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), StretchingExercisePageActivity.class)));

    }
}