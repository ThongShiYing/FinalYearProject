package com.example.healthwithme.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthwithme.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DrinkWaterReminderPageActivity extends AppCompatActivity {
    private static final String TAG = "";
    TextView textViewUserDrink, textViewTargetGoal;
    Button btnAdd, btnHistory;
    ProgressBar progressBar;
    ImageButton imgBtnHomeWater, imgBtnProfileWater;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_water_reminder);
        //Define variable
        textViewUserDrink = findViewById(R.id.UserDrink);
        textViewTargetGoal = findViewById(R.id.TargetGoal);
        btnAdd = findViewById(R.id.addbtn);
        btnHistory = findViewById(R.id.historybtn);
        progressBar = findViewById(R.id.progressBar);
        imgBtnHomeWater = findViewById(R.id.home_water);
        imgBtnProfileWater = findViewById(R.id.profile_water);


        //Connect firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        //Retrieve intake from firebase
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, (value, error) -> {
            String intake = value.getString("Intake");
            textViewTargetGoal.setText(intake);
            progressBar.setMax(Integer.parseInt(intake));


        });

        //Retrieve today drink water from firebase
        fStore.collection("water").document(userID).collection("water").whereEqualTo("date", today).addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e(TAG, "Listen failed.", error);
                return;
            }
            int todayTotalVolume = 0;

            for (QueryDocumentSnapshot doc : Objects.requireNonNull(value)) {
                todayTotalVolume += Math.toIntExact(Objects.requireNonNull(doc.getLong("waterVolume")));
            }

            textViewUserDrink.setText(String.valueOf(todayTotalVolume));
            progressBar.setProgress(todayTotalVolume);
        });


        imgBtnHomeWater.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), HomePageActivity.class)));

        imgBtnProfileWater.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ProfilePageActivity.class)));

        btnAdd.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AddWaterPageActivity.class)));

        btnHistory.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), WaterHistoryPageActivity.class)));

    }
}