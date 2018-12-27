package com.abhijit.alarmclock.Fragments;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.abhijit.alarmclock.Database.databaseHandler;
import com.abhijit.alarmclock.Database.databaseHelper;
import com.abhijit.alarmclock.MainActivity;
import com.abhijit.alarmclock.Model.alarmModel;
import com.abhijit.alarmclock.R;

public class alarmStopFragment extends Fragment  {


    private Button stop;
    TextView b;
    private  String modelId[];
    private MediaPlayer thePlayer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.alarm_stop_fragment,container,false);
       String extra= getArguments().getString("string");
       stop = v.findViewById(R.id.button);
        AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
         thePlayer = MediaPlayer.create(getActivity().getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        thePlayer.start();
        modelId = extra.split("/");
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id= Integer.parseInt(modelId[0]);
                removeAlarm(id);
                thePlayer.stop();
            }
        });
        b= v.findViewById(R.id.stop);
        String format;
        b.setText(modelId[1]);
        return v;
    }

    public void removeAlarm(int id){
        databaseHandler handler = new databaseHandler(getContext());
        handler.updateOnStop(false,id);
        Intent i =new Intent(getActivity(),MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        thePlayer.stop();
    }

}
