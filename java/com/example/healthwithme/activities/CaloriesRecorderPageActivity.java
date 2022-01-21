package com.example.healthwithme.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.healthwithme.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class CaloriesRecorderPageActivity extends AppCompatActivity {
    private static final String TAG = "";
    TextView textViewUserCalories,textViewMaxCalories;
    Button btnAdd, btnHistory;
    ProgressBar progressBar;
    ImageButton imgBtnHomeCalorie, imgBtnProfileCalorie;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_recorder_page);
        //Define variable
        textViewUserCalories = findViewById(R.id.UserCalories);
        textViewMaxCalories = findViewById(R.id.maxCalories);
        btnAdd = findViewById(R.id.addbtn_calories);
        btnHistory = findViewById(R.id.historybtn_calories);
        progressBar = findViewById(R.id.progressBar);
        imgBtnHomeCalorie = findViewById(R.id.home_calories);
        imgBtnProfileCalorie = findViewById(R.id.profile_calories);

        //Connect firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

    //Retrieve max calories from firebase
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, (value, error) -> {
            String gender = value.getString("Gender");
            if(gender.equalsIgnoreCase("male")){
                textViewMaxCalories.setText("2340");
                progressBar.setMax(Integer.parseInt("2340"));
            }
            else{
                textViewMaxCalories.setText("1900");
                progressBar.setMax(Integer.parseInt("1900"));
            }
        });

        //Retrieve today calories from firebase
        fStore.collection("calories").document(userID).collection("calories").whereEqualTo("date", today).addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e(TAG, "Listen failed.", error);
                return;
            }
            int todayTotalVolume = 0;

            for (QueryDocumentSnapshot doc : Objects.requireNonNull(value)) {
                todayTotalVolume += Math.toIntExact(Objects.requireNonNull(doc.getLong("foodCalories")));
            }

            textViewUserCalories.setText(String.valueOf(todayTotalVolume));
            progressBar.setProgress(todayTotalVolume);
        });



        imgBtnHomeCalorie.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), HomePageActivity.class)));

        imgBtnProfileCalorie.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ProfilePageActivity.class)));

        btnAdd.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AddFoodPageActivity.class)));

        btnHistory.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), CaloriesHistoryPageActivity.class)));
    }
}