package com.example.notificatioapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "BASIC_CHANNEL_ID";
    private static final String CHANNEL_NAME = "BASIC_CHANNEL_NAME";
    Button notificationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationButton = findViewById(R.id.notification_button);
        notificationButton.setOnClickListener(view -> createNotification( ) );
    }

    private void createNotification() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent( getApplicationContext(), MainActivity.class );
        PendingIntent pendingIntent = PendingIntent.getActivity(
                MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

            /* SRC: https://developer.android.com/develop/ui/views/notifications/build-notification#java
            * Register the channel with the system; you can't change the importance
            * or other notification behaviors after this. */
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DESCRIPTION");
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                MainActivity.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle("Notification Title").setContentText("Notification Body")
                .setPriority( NotificationCompat.PRIORITY_DEFAULT )
                .setAutoCancel(true);

        notificationBuilder.setContentIntent(pendingIntent);
        notificationManager.notify(0, notificationBuilder.build( ) );
    }
}