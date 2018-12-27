package com.abhijit.alarmclock.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.abhijit.alarmclock.Database.databaseHandler;
import com.abhijit.alarmclock.Fragments.alarmFragment;
import com.abhijit.alarmclock.Model.alarmModel;
import com.abhijit.alarmclock.R;
import com.abhijit.alarmclock.Utils.alarmUtils;
import com.abhijit.alarmclock.deleteListener;
import com.abhijit.alarmclock.toggleListener;

import java.util.Calendar;
import java.util.List;
public class alarm_recyclerview extends RecyclerView.Adapter<alarm_recyclerview.viewhold> {
    Context mContext;
    List<alarmModel> mAlarmModelList;
    deleteListener listener;
    toggleListener toggleListener;
    public alarm_recyclerview(Context c,List<alarmModel> list,deleteListener listener,toggleListener toggleListener){
        mContext = c;
        mAlarmModelList = list;
        this.listener = listener;
        this.toggleListener = toggleListener;
    }

    @Override
    public viewhold onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.alarm_recycler_row,parent,false);
        return new viewhold(v);
    }

    @Override
    public void onBindViewHolder(viewhold holder, int position) {
            holder.bind(mAlarmModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return mAlarmModelList.size();
    }




    public class viewhold extends RecyclerView.ViewHolder {
        private TextView alarmTime;
        private Button delete;
        private ToggleButton mToggleButton;
        private alarmModel mAlarmModel;
        private CardView mCardView;
        private TextView dayTextview;
        public viewhold(View itemView) {
            super(itemView);
            alarmTime = itemView.findViewById(R.id.alarmTime);
            mToggleButton = itemView.findViewById(R.id.toggleButton);
            mCardView  =itemView.findViewById(R.id.cardView);
            delete = itemView.findViewById(R.id.delete);
            dayTextview = itemView.findViewById(R.id.day);
        }
        public void checkDay(int day){
            Calendar c = Calendar.getInstance();
            if(day>c.get(Calendar.DAY_OF_MONTH))
            {
                dayTextview.setText("TOMORROW");
            }else{
                dayTextview.setText("TODAY");
            }
        }

        public void bind(alarmModel alarm)
        {
            mAlarmModel = alarm;
            checkDay(mAlarmModel.getDayOfmonth());
            if(mAlarmModel.isOn()){
                mToggleButton.setChecked(true);
                mCardView.setAlpha(1f);
            }else{
                mCardView.setAlpha(0.5f);
            }
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseHandler handler = new databaseHandler(mContext);
                    handler.deleteAlarm(mAlarmModel);
                    listener.onDelete();
                }
            });
            mToggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(mToggleButton.isChecked()){
                        alarm.setOn(true);
                        //update Database
                        mCardView.setAlpha(1f);
                    int dayofmonth= alarmFragment.checkTime(alarm.getHour(),alarm.getMinutes());
                        alarm.setDayOfmonth(dayofmonth);
                        String day = String.valueOf(dayofmonth);
                        databaseHandler d = new databaseHandler(mContext);
                        d.updateDatabase(true,day,alarm.getId());
                        toggleListener.onToggle();
                        alarmUtils.addAlarm(mContext,alarm.getId(),mAlarmModel, alarm.getDayOfmonth());
                    }else{
                        alarm.setOn(false);
                        mCardView.setAlpha(0.5f);
                        //update database
                        databaseHandler d = new databaseHandler(mContext);
                        d.updateDatabase(false,Integer.toString(alarm.getDayOfmonth()),alarm.getId());
                        toggleListener.onToggle();
                        alarmUtils.cancelAlarm(mContext,alarm.getId(),mAlarmModel);
                    }
                }
            });
            String formatedString = formatHour(alarm.getHour(),alarm.getMinutes());
            alarmTime.setText(formatedString);
        }


        public String formatHour(int hour,int minutes){
            int hours;
            String format;
           String minuteString=null;
           if(minutes<10){
               minuteString = "0"+Integer.toString(minutes);
           }else {
               minuteString = Integer.toString(minutes);

           }
            if(hour>12){
                hours = hour-12;
                if(hours<10){
                        format= "0"+Integer.toString(hours) + ":" +minuteString + " PM";
                }else{
                        format= Integer.toString(hours) + ":" +minuteString + " PM";
                }
            }else if(hour == 12){
                    format= Integer.toString(hour) + ":" +minuteString + " PM";
        } else if(hour < 10 ){
                    format= "0"+Integer.toString(hour) + ":" +minuteString + "AM";
            }else{
                        format= Integer.toString(hour) + ":"+minuteString + "AM";
                    }
            return format;
        }
    }



}
