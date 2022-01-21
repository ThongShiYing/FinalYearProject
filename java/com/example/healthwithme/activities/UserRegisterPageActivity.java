package com.example.healthwithme.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthwithme.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserRegisterPageActivity extends AppCompatActivity {
    EditText edit_username,edit_password,edit_email,edit_gender,edit_height,edit_weight;
    TextView username,password,email,gender,height,weight,registered;
    Button register_btn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID,Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        edit_username = findViewById(R.id.edit_username);
        edit_password = findViewById(R.id.edit_password);
        edit_email = findViewById(R.id.edit_email);
        edit_gender = findViewById(R.id.edit_gender);
        register_btn = findViewById(R.id.Update);
        registered = findViewById(R.id.registered);
        edit_height = findViewById(R.id.edit_height);
        edit_weight = findViewById(R.id.edit_weight);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

//        if(fAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(),UserLogin.class));
//            finish();
//        }

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edit_email.getText().toString().trim();
                String password = edit_password.getText().toString().trim();
                String username = edit_username.getText().toString().trim();
                String gender = edit_gender.getText().toString().trim();
                String height = edit_height.getText().toString();
                String weight = edit_weight.getText().toString();


            // Check the data is empty
                if(TextUtils.isEmpty(username)){
                    edit_username.setError("Please fill in your username.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    edit_password.setError("Please fill in your password.");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    edit_email.setError("Please fill in your email address.");
                    return;
                }
                if(TextUtils.isEmpty(gender)){
                    edit_gender.setError("Please fill in your gender.");
                    return;
                }
                if(TextUtils.isEmpty(height)){
                    edit_height.setError("Please fill in your height.");
                    return;
                }
                if(TextUtils.isEmpty(weight)){
                    edit_weight.setError("Please fill in your weight.");
                    return;
                }
                if(!TextUtils.isDigitsOnly(height)){
                    edit_height.setError("Please fill in your height with a number.");
                    return;
                }
                if(!TextUtils.isDigitsOnly(weight)){
                    edit_weight.setError("Please fill in your weight with a number.");
                    return;
                }

            //Check empty end

                if(password.length()< 6){
                    edit_password.setError("Password must be more than 6 characters.");
                    return;
                }

                //              calculate BMI
                double height_int = Integer.parseInt(height);
                double weight_int = Integer.parseInt(weight);
                double height_m = height_int/100;
                double BMI_int = weight_int/(height_m*height_m);
                String BMI_real = String.format("%.2f",BMI_int);
                String BMI = String.valueOf(BMI_real);
//              calculate end

                //check BMI Status (normal...obese)
                if(BMI_int<18.5){
                    Status = "Underweight";
                }
                if (BMI_int>=18.5 && BMI_int<=24.9){
                    Status = "Normal Weight";
                }
                if (BMI_int>=25.0 && BMI_int<=29.9){
                    Status = "Overweight";
                }
                if(BMI_int>30){
                    Status = "Obese";
                }
                //check BMI status end

//                calculate water intake goal
                int intake_double = (int) (weight_int*30);
                String intake = String.valueOf(intake_double);
//                intake goal end

                //register the user in firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(UserRegisterPageActivity.this, "Register Successfully!", Toast.LENGTH_SHORT).show();
                            // sotre data into fire store
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Username",username);
                            user.put("Email",email);
                            user.put("Gender",gender);
                            user.put("Height",height);
                            user.put("Weight",weight);
                            user.put("BMI",BMI);
                            user.put("Status",Status);
                            user.put("Intake",intake);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("TAG","User Profile is created for" + username);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG","Failed! "+e.toString());
                                }
                            });

                            DocumentReference documentRefWater = fStore.collection("water").document(userID);
                            Map<String,Object> water = new HashMap<>();
                            water.put("Total","0");

                            documentRefWater.set(water).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("TAG","Total Water Amount 0");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG","Failed! "+e.toString());
                                }
                            });


                            startActivity(new Intent(getApplicationContext(), UserLoginPageActivity.class));
                        }else{
                            Toast.makeText(UserRegisterPageActivity.this, "Error! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    registered.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), UserLoginPageActivity.class));
        }
    });

    }
}