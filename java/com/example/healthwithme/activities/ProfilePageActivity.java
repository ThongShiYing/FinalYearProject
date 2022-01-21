package com.example.healthwithme.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.healthwithme.BreakfastRecord;
import com.example.healthwithme.DinnerRecord;
import com.example.healthwithme.EditInfo;
import com.example.healthwithme.ExerciseRecord;
import com.example.healthwithme.LunchRecord;
import com.example.healthwithme.MainActivity;
import com.example.healthwithme.R;
import com.example.healthwithme.WaterReminder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfilePageActivity extends AppCompatActivity {
    TextView username, password, email, gender, height, weight, BMI, status, waterGoal;
    Switch drinkSwitch, breakfastSwitch,lunchSwitch, dinnerSwitch, exerciseSwitch;
    Button editInfo,logout;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createDrinkNotification();
        setContentView(R.layout.activity_profile_page);
//      define variable
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.Email);
        gender = findViewById(R.id.Gender);
        height = findViewById(R.id.height_user);
        weight = findViewById(R.id.weight_user);
        BMI = findViewById(R.id.BMI);
        status = findViewById(R.id.status);
        drinkSwitch = findViewById(R.id.drinkSwitch);
        breakfastSwitch = findViewById(R.id.breakfastSwitch);
        lunchSwitch = findViewById(R.id.lunchSwitch);
        dinnerSwitch = findViewById(R.id.DinnerSwitch);
        exerciseSwitch = findViewById(R.id.exerciseSwitch);
        waterGoal = findViewById(R.id.waterGoal);
        editInfo = findViewById(R.id.editInfo);
       // logout = findViewById(R.id.logout);

//      connect firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

//      retrieve data from firebase
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                username.setText(value.getString("Username"));
                email.setText(value.getString("Email"));
                gender.setText(value.getString("Gender"));
                height.setText(value.getString("Height"));
                weight.setText(value.getString("Weight"));
                BMI.setText(value.getString("BMI"));
                status.setText(value.getString("Status"));
                waterGoal.setText(value.getString("Intake"));


            }
        });


        drinkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Intent intent = new Intent(ProfilePageActivity.this, WaterReminder.class);
                    PendingIntent broadcast = PendingIntent.getBroadcast(ProfilePageActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                    Calendar cal = Calendar.getInstance();

                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                    alarmManager.setRepeating(AlarmManager.RTC, cal.getTimeInMillis(), 1000 * 60 * 90, broadcast);
                }else{
                    Intent intent = new Intent(ProfilePageActivity.this, WaterReminder.class);
                    PendingIntent sender = PendingIntent.getBroadcast(ProfilePageActivity.this, 0, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                    alarmManager.cancel(sender);
                }
            }
        });

        breakfastSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Intent intent1 = new Intent(ProfilePageActivity.this, BreakfastRecord.class);
                    PendingIntent broadcast1 = PendingIntent.getBroadcast(ProfilePageActivity.this,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);

                    AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);

                    Calendar alarmStartTime = Calendar.getInstance();
                    alarmStartTime.set(Calendar.HOUR_OF_DAY, 10);
                    alarmStartTime.set(Calendar.MINUTE, 00);
                    alarmStartTime.set(Calendar.SECOND, 0);

                    alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast1);
                }else{
                    Intent intent = new Intent(ProfilePageActivity.this, BreakfastRecord.class);
                    PendingIntent sender = PendingIntent.getBroadcast(ProfilePageActivity.this, 0, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                    alarmManager.cancel(sender);
                }
            }
        });

        lunchSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Intent intent1 = new Intent(ProfilePageActivity.this, LunchRecord.class);
                    PendingIntent broadcast1 = PendingIntent.getBroadcast(ProfilePageActivity.this,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);

                    AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);

                    Calendar alarmStartTime = Calendar.getInstance();
                    alarmStartTime.set(Calendar.HOUR_OF_DAY, 14);
                    alarmStartTime.set(Calendar.MINUTE, 00);
                    alarmStartTime.set(Calendar.SECOND, 0);

                    alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast1);
                }else{
                    Intent intent = new Intent(ProfilePageActivity.this, LunchRecord.class);
                    PendingIntent sender = PendingIntent.getBroadcast(ProfilePageActivity.this, 0, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                    alarmManager.cancel(sender);
                }
            }
        });

        dinnerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Intent intent1 = new Intent(ProfilePageActivity.this, DinnerRecord.class);
                    PendingIntent broadcast1 = PendingIntent.getBroadcast(ProfilePageActivity.this,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);

                    AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);

                    Calendar alarmStartTime = Calendar.getInstance();
                    alarmStartTime.set(Calendar.HOUR_OF_DAY, 20);
                    alarmStartTime.set(Calendar.MINUTE, 00);
                    alarmStartTime.set(Calendar.SECOND, 0);

                    alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast1);
                }else{
                    Intent intent = new Intent(ProfilePageActivity.this, DinnerRecord.class);
                    PendingIntent sender = PendingIntent.getBroadcast(ProfilePageActivity.this, 0, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                    alarmManager.cancel(sender);
                }
            }
        });

        exerciseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Intent intent1 = new Intent(ProfilePageActivity.this, ExerciseRecord.class);
                    PendingIntent broadcast1 = PendingIntent.getBroadcast(ProfilePageActivity.this,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);

                    AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);

                    Calendar alarmStartTime = Calendar.getInstance();
                    alarmStartTime.set(Calendar.HOUR_OF_DAY, 18);
                    alarmStartTime.set(Calendar.MINUTE, 00);
                    alarmStartTime.set(Calendar.SECOND, 0);

                    alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast1);
                }else{
                    Intent intent = new Intent(ProfilePageActivity.this, ExerciseRecord.class);
                    PendingIntent sender = PendingIntent.getBroadcast(ProfilePageActivity.this, 0, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                    alarmManager.cancel(sender);
                }
            }
        });

        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditInfo.class));
            }
        });

//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fAuth.signOut();
//                signOutUser();
//            }
//        });




    }

    private void createDrinkNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "HealthWithMe";
            String description = "Channel for drink Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("drinkNotification",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createBreakfastNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "HealthWithMe";
            String description = "Channel for breakfast Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("breakfastNotification",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createLunchNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "HealthWithMe";
            String description = "Channel for lunch Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("lunchNotification",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createDinnerNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "HealthWithMe";
            String description = "Channel for dinner Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("dinnerNotification",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createExerciseNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "HealthWithMe";
            String description = "Channel for exercise Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("exerciseNotification",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
//    private void signOutUser(){
//        Intent mainActivity = new Intent(ProfilePageActivity.this,MainActivity.class);
//        mainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(mainActivity);
//        finish();
//    }
}