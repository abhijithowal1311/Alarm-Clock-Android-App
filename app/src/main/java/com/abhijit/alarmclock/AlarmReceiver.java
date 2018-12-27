package com.abhijit.alarmclock;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.abhijit.alarmclock.Model.alarmModel;

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
       String extra =intent.getStringExtra("broadcast");
        Intent intent1 = new Intent();
        intent1.putExtra("int1",extra);
        intent1.setClassName(context.getPackageName(), alarmStopActivity.class.getName());
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);

    }
}

