package com.example.healthwithme.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthwithme.R;
import com.google.firebase.auth.FirebaseAuth;

public class UserLoginPageActivity extends AppCompatActivity {
    EditText edit_email, edit_password;
    Button login_btn;
    TextView registered, forgot;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        edit_email = findViewById(R.id.edit_username);
        edit_password = findViewById(R.id.edit_password);
        login_btn = findViewById(R.id.Update);
        registered = findViewById(R.id.registered);
        forgot = findViewById(R.id.forgot);
        fAuth = FirebaseAuth.getInstance();

        login_btn.setOnClickListener(v -> {
            String email = edit_email.getText().toString().trim();
            String password = edit_password.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                edit_email.setError("Please fill in your email address.");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                edit_password.setError("Please fill in your password.");
                return;
            }

            //auth the user
            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(UserLoginPageActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(UserLoginPageActivity.this, HomePageActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(UserLoginPageActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
        registered.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), UserRegisterPageActivity.class)));

        forgot.setOnClickListener(v -> {
            final EditText resetMail = new EditText(v.getContext());
            final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
            passwordResetDialog.setTitle("Reset Password ?");
            passwordResetDialog.setMessage("Enter Your Email To Receive Reset Link.");
            passwordResetDialog.setView(resetMail);


            passwordResetDialog.setPositiveButton("OK", (dialog, which) -> {
                //extract email and send reset link
                String mail = resetMail.getText().toString();
                if (TextUtils.isEmpty(mail)) {
                    return;
                }

                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(unused ->
                        {
                            Toast.makeText(UserLoginPageActivity.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                        }
                ).addOnFailureListener(e -> {
                    Toast.makeText(UserLoginPageActivity.this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            });
            passwordResetDialog.create().show();
        });
    }
}