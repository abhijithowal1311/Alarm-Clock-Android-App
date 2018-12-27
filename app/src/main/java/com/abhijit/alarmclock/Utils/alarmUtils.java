package com.abhijit.alarmclock.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.AlarmManagerCompat;

import com.abhijit.alarmclock.AlarmReceiver;
import com.abhijit.alarmclock.Model.alarmModel;

import java.util.Calendar;

public class alarmUtils {


    public static void addAlarm(Context context, int id,alarmModel a,int dayofMonth){
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,a.getHour());
        c.set(Calendar.DAY_OF_MONTH,dayofMonth);
        c.set(Calendar.MINUTE,a.getMinutes());
        Intent intent = new Intent(context, AlarmReceiver.class);
        String extra = a.getId()+"/"+a.getHour()+":"+a.getMinutes();
        intent.putExtra("broadcast",extra);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManagerCompat.setExactAndAllowWhileIdle(alarmMgr,AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),alarmIntent);
    }


    public static void cancelAlarm(Context context,int id,alarmModel a){
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("broadcast",a);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        assert alarmMgr != null;
        alarmMgr.cancel(alarmIntent);
    }
}
