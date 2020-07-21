package com.example.alarmmanager;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class PlayingServices extends Service {
    MediaPlayer media_song;
    int startId;
    boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        // fetch the extra string from the alarm on/alarm off values
        String state = intent.getExtras().getString("extra");
        // fetch the whale choice integer values
        Integer whale_sound_choice = intent.getExtras().getInt("whale_choice");

        Log.e("Ringtone extra is ", state);
        Log.e("Whale choice is ", whale_sound_choice.toString());

        // put the notification here, test it out

        // notification
        // set up the notification service
        NotificationManager notify_manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        // set up an intent that goes to the Main Activity
        Intent intent_main_activity = new Intent(this.getApplicationContext(), MainActivity.class);
        // set up a pending intent
        PendingIntent pending_intent_main_activity = PendingIntent.getActivity(this, 0,
                intent_main_activity, 0);

        // make the notification parameters
        Notification notification_popup = new Notification.Builder(this)
                .setContentTitle("An alarm is going off!")
                .setContentText("Click me!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pending_intent_main_activity)
                .setAutoCancel(true)
                .build();
        // this converts the extra strings from the intent
        // to start IDs, values 0 or 1
        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                Log.e("Start ID is ", state);
                break;
            default:
                startId = 0;
                break;
        }
        // if else statements
        notify_manager.notify(0, notification_popup);



        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // Tell the user we stopped.
        Log.e("on Destroy called", "ta da");
        super.onDestroy();
        this.isRunning = false;
    }
}

