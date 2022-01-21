package com.example.healthwithme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.healthwithme.activities.HomePageActivity;
import com.example.healthwithme.activities.UserLoginPageActivity;
import com.example.healthwithme.activities.UserRegisterPageActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button btnRegister, btnLogin;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegister = findViewById(R.id.register_main);
        btnLogin = findViewById(R.id.Login_main);

        fAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), UserRegisterPageActivity.class)));
        btnLogin.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), UserLoginPageActivity.class)));
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        //If user has login direct switch to HomePage
//        if (fAuth.getCurrentUser() != null){
//            Intent intent = new Intent();
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setClass(MainActivity.this, HomePageActivity.class);
//            startActivity(intent);
//        }
//
//
//    }
}