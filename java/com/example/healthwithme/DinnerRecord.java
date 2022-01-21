package com.example.healthwithme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class DinnerRecord extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"dinnerNotification");
        builder.setContentTitle("Calories Recorder");
        builder.setContentText("Is time to record the calories of your dinner!");
        builder.setSmallIcon(R.drawable.iconapp);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(1,builder.build());
    }
}
