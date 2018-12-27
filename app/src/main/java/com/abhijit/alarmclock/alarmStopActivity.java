package com.abhijit.alarmclock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.abhijit.alarmclock.Fragments.alarmStopFragment;
import com.abhijit.alarmclock.Fragments.clockFragment;
import com.abhijit.alarmclock.Model.alarmModel;


public class alarmStopActivity extends AppCompatActivity {
    private FragmentManager mFragmentManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        Fragment fragment =mFragmentManager.findFragmentById(R.id.container);
        if(fragment == null){
            Bundle b =new Bundle();
            String a= getIntent().getStringExtra("int1");
            b.putSerializable("string",a);
            fragment = new alarmStopFragment();
            fragment.setArguments(b);
            mFragmentManager.beginTransaction().add(R.id.container,fragment).commit();

        }
    }

}
