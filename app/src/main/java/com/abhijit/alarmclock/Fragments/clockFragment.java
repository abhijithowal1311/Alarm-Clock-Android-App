package com.abhijit.alarmclock.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.abhijit.alarmclock.R;
import com.abhijit.alarmclock.Utils.timeUtil;
import com.abhijit.alarmclock.alarmActivity;

import org.reactivestreams.Subscription;

import java.time.Clock;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
public class clockFragment extends Fragment {
    private Disposable subscription;
    private TextView dateView;
    private TextView time;
    private Button alarm;

    @Override
    public void onResume() {
        super.onResume();
        if(subscription.isDisposed()){
            subscription =Observable.interval(0,1000,TimeUnit.MILLISECONDS).doOnError(throwable -> nothing())
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(tick -> getTime());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.clock_fragment,container,false);
        time = v.findViewById(R.id.clockView);
        dateView = v.findViewById(R.id.clockDate);
        alarm = v.findViewById(R.id.alarm);
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAlarmAct();
            }
        });


        //   RXjava disposable for displaying time
        subscription =Observable.interval(0,1000,TimeUnit.MILLISECONDS).doOnError(throwable -> nothing())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(tick -> getTime());
        return v;
    }

    public void nothing(){

    }
    public void launchAlarmAct(){
        Intent i = new Intent(getActivity(),alarmActivity.class);
        startActivity(i);
    }

    public void getTime(){
        timeUtil t =new timeUtil();
        time.setText(t.getTime());
        dateView.setText(t.getDate());
  }

    @Override
    public void onPause() {
        super.onPause();
        subscription.dispose();
    }

}
