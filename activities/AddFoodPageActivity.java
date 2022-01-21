package com.example.healthwithme.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthwithme.R;
import com.example.healthwithme.model.FoodCalories;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddFoodPageActivity extends AppCompatActivity {
    TextView cal607,cal225,cal204,cal408,cal252,cal220,cal240,cal273,cal227,cal67,cal192,cal247,cal120,cal60,cal38,cal71,cal53,cal179,cal135;
    ImageButton OtherFoods;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID, total;
    int totalInt;

    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_page);
        //Define variable
        cal607 = findViewById(R.id.Cal607);
        cal225 = findViewById(R.id.Cal225);
        cal204 = findViewById(R.id.Cal204);
        cal408 = findViewById(R.id.Cal408);
        cal252 = findViewById(R.id.Cal252);
        cal220 = findViewById(R.id.Cal220);
        cal240 = findViewById(R.id.Cal240);
        cal273 = findViewById(R.id.Cal273);
        cal227 = findViewById(R.id.Cal227);
        cal67 = findViewById(R.id.Cal67);
        cal192 = findViewById(R.id.Cal192);
        cal247 = findViewById(R.id.Cal247);
        cal120 = findViewById(R.id.Cal120);
        cal60 = findViewById(R.id.Cal60);
        cal38 = findViewById(R.id.Cal38);
        cal71 = findViewById(R.id.Cal71);
        cal53 = findViewById(R.id.Cal53);
        cal179 = findViewById(R.id.Cal179);
        cal135 = findViewById(R.id.Cal135);
        OtherFoods = findViewById(R.id.OtherFoods);

        //Connect firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentRef = fStore.collection("calories").document(userID);
        documentRef.addSnapshotListener(this, (value, error) -> {
            if (value.exists()) {
                total = (value.getString("Total"));
                totalInt = Integer.parseInt(total);
            } else {
                totalInt = 0;
            }

        });

        cal607.setOnClickListener(view -> updateCalories("Chicken Rice","1 Plate",607));
        cal225.setOnClickListener(view -> updateCalories("Egg Fried Rice","1 Plate",225));
        cal204.setOnClickListener(view -> updateCalories("White Rice","1 Bowl",204));
        cal408.setOnClickListener(view -> updateCalories("Curry Rice","1 Plate",408));
        cal252.setOnClickListener(view -> updateCalories("Pork Fried Rice","1 Plate",252));
        cal220.setOnClickListener(view -> updateCalories("Spaghetti","1 Plate",220));
        cal240.setOnClickListener(view -> updateCalories("Fried Noodles","1 Plate",240));
        cal273.setOnClickListener(view -> updateCalories("Instant Noodles","1 Plate",273));
        cal227.setOnClickListener(view -> updateCalories("Noodles","1 Plate",227));
        cal67.setOnClickListener(view -> updateCalories("Whole Wheat Bread","1 Slice",67));
        cal192.setOnClickListener(view -> updateCalories("French Fries","70g",192));
        cal247.setOnClickListener(view -> updateCalories("Sandwich Biscuits","1 Pack",247));
        cal120.setOnClickListener(view -> updateCalories("Chocolate Cupcake","1 Cup",120));
        cal60.setOnClickListener(view -> updateCalories("Popcorn","1 Bowl",60));
        cal38.setOnClickListener(view -> updateCalories("Coffee","250 ml",38));
        cal71.setOnClickListener(view -> updateCalories("Latte","250 ml",71));
        cal53.setOnClickListener(view -> updateCalories("Cappuccino","250 ml",53));
        cal179.setOnClickListener(view -> updateCalories("Soft drink","350 ml",179));
        cal135.setOnClickListener(view -> updateCalories("Soya Milk","250 ml",135));

        OtherFoods.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AddOtherFoodsActivity.class)));
    }

    private void updateCalories(String name,String portion,int calories){
        FoodCalories foodCalories = new FoodCalories(name,portion,calories,date,time);

        fStore.collection("calories").document(userID).collection("calories").document()
                .set(foodCalories)
                .addOnSuccessListener(runnable -> {
                    Toast.makeText(AddFoodPageActivity.this, "Calories Recorded.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), CaloriesRecorderPageActivity.class));
                })
                .addOnFailureListener(e -> {
                    Log.e("FireStore", e.getMessage());
                    Toast.makeText(AddFoodPageActivity.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}