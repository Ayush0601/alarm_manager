package com.example.alarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


public class MyBroadCastReceiver extends BroadcastReceiver {
    String id;
    String time;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent !=null && intent.getExtras()!=null){
            id = intent.getExtras().getString("id");
            time = intent.getExtras().getString("time");
        }


            Intent serviceIntent = new Intent(context, BackgroundService.class);
            Bundle mBundle = new Bundle();
            mBundle.putString("id",id);
            mBundle.putString("time", time);
            serviceIntent.putExtras(mBundle);
            context.startService(serviceIntent);


    }
}