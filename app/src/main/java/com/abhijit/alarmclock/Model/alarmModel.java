package com.abhijit.alarmclock.Model;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.UUID;

public class alarmModel implements Serializable {
    private boolean isOn ;
    private int Hour;
    private int id;
    private int minutes;
    private int dayOfmonth;
    public int getId() {
        return id;
    }

    public int getDayOfmonth() {
        return dayOfmonth;
    }

    public void setDayOfmonth(int dayOfmonth) {
        this.dayOfmonth = dayOfmonth;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOn() {
        return isOn;
    }


    public void setOn(boolean on) {
        isOn = on;
    }

    public int getHour() {
        return Hour;
    }

    public void setHour(int hour) {
        Hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public alarmModel(int hour, int minutes, boolean isOn) {
        this.isOn = isOn;
        Hour = hour;
        this.minutes = minutes;
    }
}
