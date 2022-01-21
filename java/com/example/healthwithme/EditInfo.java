package com.example.healthwithme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthwithme.activities.ProfilePageActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class EditInfo extends AppCompatActivity {
    EditText edit_username,edit_email,edit_gender,edit_height,edit_weight;
    Button update;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID,status;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        edit_username = findViewById(R.id.edit_username);
        edit_email = findViewById(R.id.edit_email);
        edit_gender = findViewById(R.id.edit_gender);
        edit_height = findViewById(R.id.edit_height);
        edit_weight = findViewById(R.id.edit_weight);
        update = findViewById(R.id.Update);

        //      connect firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        userID = fAuth.getCurrentUser().getUid();

        //      retrieve data from firebase
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                edit_username.setText(value.getString("Username"));
                edit_email.setText(value.getString("Email"));
                edit_gender.setText(value.getString("Gender"));
                edit_height.setText(value.getString("Height"));
                edit_weight.setText(value.getString("Weight"));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edit_email.getText().toString().trim();
                String username = edit_username.getText().toString().trim();
                String gender = edit_gender.getText().toString().trim();
                String height = edit_height.getText().toString();
                String weight = edit_weight.getText().toString();

                // Check the data is empty
                if(TextUtils.isEmpty(username)){
                    edit_username.setError("Please fill in your username.");
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
                    status = "Underweight";
                }
                if (BMI_int>=18.5 && BMI_int<=24.9){
                    status = "Normal Weight";
                }
                if (BMI_int>=25.0 && BMI_int<=29.9){
                    status = "Overweight";
                }
                if(BMI_int>30){
                    status = "Obese";
                }
                //check BMI status end

//                calculate water intake goal
                int intake_double = (int) (weight_int*30);
                String intake = String.valueOf(intake_double);
//                intake goal end

                //update data
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        DocumentReference docRef = fStore.collection("users").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("Username",username);
                        edited.put("Email",email);
                        edited.put("Gender",gender);
                        edited.put("Height",height);
                        edited.put("Weight",weight);
                        edited.put("BMI",BMI);
                        edited.put("Intake",intake);
                        edited.put("Status",status);
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(EditInfo.this,"Profile Updated.",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), ProfilePageActivity.class));
                                finish();
                            }
                        });
                        //Toast.makeText(EditInfo.this, "Email is changed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditInfo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

            }
        });
    }
}