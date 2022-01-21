package com.example.healthwithme.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthwithme.R;
import com.example.healthwithme.model.DrinkWater;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddWaterPageActivity extends AppCompatActivity {
    TextView ml100, ml125, ml150, ml175, ml200, ml300, ml400;
    ImageButton home_addwater, profile_addwater;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID, total;
    int totalInt;

    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_water);
        //Define variable
        ml100 = findViewById(R.id.ml100);
        ml125 = findViewById(R.id.ml125);
        ml150 = findViewById(R.id.ml150);
        ml175 = findViewById(R.id.ml175);
        ml200 = findViewById(R.id.ml200);
        ml300 = findViewById(R.id.ml300);
        ml400 = findViewById(R.id.ml400);
        home_addwater = findViewById(R.id.home_addwater);
        profile_addwater = findViewById(R.id.profile_addwater);


        //Connect firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentRef = fStore.collection("water").document(userID);
        documentRef.addSnapshotListener(this, (value, error) -> {
            if (value.exists()) {
                total = (value.getString("Total"));
                totalInt = Integer.parseInt(total);
            } else {
                totalInt = 0;
            }

        });

        ml100.setOnClickListener(view -> updateWater(100));
        ml125.setOnClickListener(view -> updateWater(125));
        ml150.setOnClickListener(view -> updateWater(150));
        ml175.setOnClickListener(view -> updateWater(175));
        ml200.setOnClickListener(view -> updateWater(200));
        ml300.setOnClickListener(view -> updateWater(300));
        ml400.setOnClickListener(view -> updateWater(400));

        home_addwater.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), HomePageActivity.class)));
        profile_addwater.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ProfilePageActivity.class)));
    }

    private void updateWater(int waterVolume) {

        DrinkWater drinkWater = new DrinkWater(waterVolume,date,time);

        fStore.collection("water").document(userID).collection("water").document()
                .set(drinkWater)
                .addOnSuccessListener(runnable -> {
                    Toast.makeText(AddWaterPageActivity.this, "Amount Recorded.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), DrinkWaterReminderPageActivity.class));
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStore", e.getMessage());
                    Toast.makeText(AddWaterPageActivity.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });


    }
}