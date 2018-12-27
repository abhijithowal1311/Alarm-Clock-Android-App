package com.abhijit.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.AlarmManagerCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.abhijit.alarmclock.Fragments.clockFragment;

import java.util.Calendar;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    private static MainActivity inst;
    private TimePicker mTimePicker;
    private Button mButton;
    private FragmentManager mFragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        Fragment fragment =mFragmentManager.findFragmentById(R.id.container);
        if(fragment == null){
            fragment = new clockFragment();
            mFragmentManager.beginTransaction().add(R.id.container,fragment).commit();

        }

    }
    public void log(String logs){
        System.out.println(logs);
    }
}
