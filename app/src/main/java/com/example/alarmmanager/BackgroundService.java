package com.example.alarmmanager;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;


import java.util.Timer;
import java.util.TimerTask;


@SuppressLint("Registered")
public class BackgroundService extends Service {

    private NotificationManager notificationManager;
    float distance = 0;
    float dist = 0;
    int notification_id = 1001;
    String booking_id;
    String id;
    String time;
    String token;
    Context context = this;
    private Handler handler;
    Runnable runnable;

    public BackgroundService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();


    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent myIntent, int flags, int startId) {


        // this getter is just for example purpose, can differ
        if (myIntent != null && myIntent.getExtras() != null) {
            id = myIntent.getExtras().getString("id");
            time = myIntent.getExtras().getString("time");

        }


        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        PendingIntent service = PendingIntent.getService(
                getApplicationContext(),
                1001,
                new Intent(getApplicationContext(), BackgroundService.class),
                PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, service);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void buildNotification(String km) {

        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(ns);



        //the intent that is started when the notification is clicked (works)
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle(time)
                .setContentText(id)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingNotificationIntent)
                .setAutoCancel(true)
                .build();


        notification.flags |= Notification.FLAG_FOREGROUND_SERVICE;







    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    public void clearNotification() {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notification_id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
