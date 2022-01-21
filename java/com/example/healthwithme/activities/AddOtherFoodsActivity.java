package com.example.healthwithme.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.healthwithme.R;
import com.example.healthwithme.model.FoodCalories;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddOtherFoodsActivity extends AppCompatActivity {
    EditText FoodName,Portion,TotalCalories;
    Button AddFood;
    ImageButton home_others,profile_others;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_other_foods);

        FoodName = findViewById(R.id.FoodName);
        Portion = findViewById(R.id.Portion);
        TotalCalories = findViewById(R.id.TotalCalories);
        AddFood = findViewById(R.id.AddFood);
        home_others = findViewById(R.id.home_others);
        profile_others = findViewById(R.id.profile_calories);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        home_others.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), HomePageActivity.class)));
        profile_others.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ProfilePageActivity.class)));

        AddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foodName = FoodName.getText().toString().trim();
                String portion = Portion.getText().toString();
                String calories_string = TotalCalories.getText().toString();
                //int calories = Integer.parseInt(TotalCalories.getText().toString());


                // Check the data is empty
                if(TextUtils.isEmpty(foodName)){
                    FoodName.setError("Please fill in the food name.");
                    return;
                }
                if(TextUtils.isEmpty(portion)){
                    Portion.setError("Please fill in the portion of the food.");
                    return;
                }
                if(TextUtils.isEmpty(calories_string)){
                    TotalCalories.setError("Please fill in the calories of the food.");
                    return;
                }

                if(!TextUtils.isDigitsOnly(calories_string)){
                    TotalCalories.setError("Please fill in the calories with a number.");
                    return;
                }
                int calories = Integer.parseInt(calories_string);

                FoodCalories foodCalories = new FoodCalories(foodName,portion,calories,date,time);

                fStore.collection("calories").document(userID).collection("calories").document()
                        .set(foodCalories)
                        .addOnSuccessListener(runnable -> {
                            Toast.makeText(AddOtherFoodsActivity.this, "Calories Recorded.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), CaloriesRecorderPageActivity.class));
                        })
                        .addOnFailureListener(e -> {
                            Log.e("FireStore", e.getMessage());
                            Toast.makeText(AddOtherFoodsActivity.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }

        });
    }
}