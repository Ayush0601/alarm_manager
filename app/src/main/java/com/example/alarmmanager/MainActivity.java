package com.example.alarmmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView timePicker, alarmOne, alarmTwo, alarmThree, alarmFour, alarmFive, alarmSix;
    String alarm_One, alarm_Two, alarm_Three, alarm_Four, alarm_Five, alarm_Six;
    AlarmManager alarmManager;
    Intent alarmIntent;
    PendingIntent pending_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        timePicker = findViewById(R.id.timePicker);
        alarmOne = findViewById(R.id.alaramOne);
        alarmTwo = findViewById(R.id.alarmTwo);
        alarmThree = findViewById(R.id.alarmThree);
        alarmFour = findViewById(R.id.alarmFour);
        alarmFive = findViewById(R.id.alarmFive);
        alarmSix = findViewById(R.id.alarmSix);

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                 alarmIntent = new Intent(MainActivity.this, MyBroadCastReceiver.class);
                pending_intent = PendingIntent.getBroadcast(MainActivity.this, 0,
                        alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (null == alarm_One) {
                            alarmOne.setText(selectedHour + ":" + selectedMinute);
                            alarm_One = selectedHour + ":" + selectedMinute;
                            alarmManager.set(AlarmManager.RTC_WAKEUP, mcurrentTime.getTimeInMillis(),
                                    pending_intent);
                            callAlarm("",selectedHour + ":" + selectedMinute);
                        } else if (null == alarm_Two) {
                            alarmTwo.setText(selectedHour + ":" + selectedMinute);
                            alarm_Two = selectedHour + ":" + selectedMinute;
                        } else if (null == alarm_Three) {
                            alarmThree.setText(selectedHour + ":" + selectedMinute);
                            alarm_Three = selectedHour + ":" + selectedMinute;
                        } else if (null == alarm_Four) {
                            alarmFour.setText(selectedHour + ":" + selectedMinute);
                            alarm_Four = selectedHour + ":" + selectedMinute;
                        } else if (null == alarm_Five) {
                            alarmFive.setText(selectedHour + ":" + selectedMinute);
                            alarm_Five = selectedHour + ":" + selectedMinute;
                        } else if (null == alarm_Six) {
                            alarmSix.setText(selectedHour + ":" + selectedMinute);
                            alarm_Six = selectedHour + ":" + selectedMinute;
                        }
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
    }

    private void callAlarm(String id, String time) {

        Bundle mBundle = new Bundle();
        mBundle.putString("id", id);
        mBundle.putString("time", time);
        alarmIntent.putExtras(mBundle);
        Intent serviceIntent = new Intent(MainActivity.this, BackgroundService.class);
        mBundle = new Bundle();
        mBundle.putString("id", id);
        mBundle.putString("total_distance", time);
        serviceIntent.putExtras(mBundle);
        MainActivity.this.startService(serviceIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, 60000, pending_intent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, 60000, pending_intent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, 60000, pending_intent);
        }
    }
}