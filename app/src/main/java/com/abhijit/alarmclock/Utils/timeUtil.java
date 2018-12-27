package com.abhijit.alarmclock.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class timeUtil {
    Calendar c;
    String time;
   public timeUtil(){
       c = Calendar.getInstance(TimeZone.getDefault());
    }

    public String getTime(){
        Date date =  c.getTime();
        time = date.toString().substring(11,19);
        return time;
    }

    public String getDate(){
        int currentyear= c.get(Calendar.YEAR);
        Date date =c.getTime();
        String dated= date.toString().substring(0,10);
        String datefinal=dated+","+Integer.toString(currentyear);

        return datefinal;
    }

}
