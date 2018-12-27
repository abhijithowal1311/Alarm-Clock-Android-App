package com.abhijit.alarmclock.Fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.AlarmManagerCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.abhijit.alarmclock.AlarmReceiver;
import com.abhijit.alarmclock.Database.databaseHandler;
import com.abhijit.alarmclock.MainActivity;
import com.abhijit.alarmclock.Model.alarmModel;
import com.abhijit.alarmclock.R;
import com.abhijit.alarmclock.Utils.alarmUtils;
import com.abhijit.alarmclock.adapters.alarm_recyclerview;
import com.abhijit.alarmclock.deleteListener;
import com.abhijit.alarmclock.toggleListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class alarmFragment extends Fragment implements deleteListener,toggleListener {
    private RecyclerView mRecyclerView;
    private AlarmManager alarmMgr;
    private TextView alarmsSet;
   static List<alarmModel> mAlarmModelList;
    private Button addAlarm;
    private int x=0;
    private PendingIntent alarmIntent;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAlarmsfromDatabase();
    }

    public void getAlarmsfromDatabase(){
        databaseHandler handler =new databaseHandler(getContext());
        mAlarmModelList = handler.getAlarmsFromDatabase();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alarm_fragment,container,false);
        mRecyclerView = v.findViewById(R.id.recyclerview);
        addAlarm = v.findViewById(R.id.addAlarm);
        alarmsSet = v.findViewById(R.id.textView);
        setTextview();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if(mAlarmModelList!=null){
            updateUI();
        }else{
            mAlarmModelList =new ArrayList<>();
             }
        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              showHourPicker();
            }
        });
        return v;
    }

    public void setTextview(){
        boolean areAlarmsSet=checkSetAlarms();
        if(!areAlarmsSet){
            alarmsSet.setText("No Alarms Set");
        }else{
            alarmsSet.setText("Alarms");
        }
    }

    public boolean checkSetAlarms(){
        for(int i=0;i<mAlarmModelList.size();i++){
            if(mAlarmModelList.get(i).isOn()){
             return true;
            }
        }
        return false;
    }
    public void updateUI(){
        alarm_recyclerview adapter  = new alarm_recyclerview(getContext(),mAlarmModelList,this,this);
        mRecyclerView.setAdapter(adapter);
        setTextview();
    }

    public void showHourPicker() {
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);
        final int[] dayofM = new int[1];
        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                     dayofM[0] = checkTime(hourOfDay,minute);
                    myCalender.set(Calendar.DAY_OF_MONTH, dayofM[0]);
                    alarmModel a = new alarmModel(hourOfDay,minute,true);
                    a.setDayOfmonth(dayofM[0]);
                    databaseHandler handler =new databaseHandler(getContext());
                    int id=handler.addAlarmToDatabase(a);
                    a.setId(id);
                    mAlarmModelList.add(a);
                    alarmUtils.addAlarm(getContext(),a.getId(),a,a.getDayOfmonth());
                    updateUI();
                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, false);
        timePickerDialog.setTitle("Set Alarm:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    public static int checkTime(int hour,int minute){
        Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        int dayofMonth=c.get(Calendar.DAY_OF_MONTH);
        if(hour==hours){
            if(minute<=minutes){
                dayofMonth = dayofMonth+1;
                return dayofMonth;
            }
        }else if(hour<hours){
           dayofMonth= dayofMonth+1;
           return dayofMonth;
        }else{
            return dayofMonth;
        }
        return dayofMonth;
    }

    @Override
    public void onDelete() {
        getAlarmsfromDatabase();
        updateUI();
    }

    @Override
    public void onToggle() {
        getAlarmsfromDatabase();
        setTextview();
        updateUI();
    }
}
