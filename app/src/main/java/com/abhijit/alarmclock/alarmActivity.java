package com.abhijit.alarmclock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.abhijit.alarmclock.Fragments.alarmFragment;
import com.abhijit.alarmclock.Fragments.clockFragment;

public class alarmActivity extends AppCompatActivity {
    private FragmentManager mFragmentManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        Fragment fragment =mFragmentManager.findFragmentById(R.id.container);
        if(fragment == null){
            fragment = new alarmFragment();
            mFragmentManager.beginTransaction().add(R.id.container,fragment).commit();
        }

    }
}
