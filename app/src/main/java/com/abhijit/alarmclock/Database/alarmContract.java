package com.abhijit.alarmclock.Database;

import android.provider.BaseColumns;

public final class alarmContract {
    private alarmContract(){}
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + alarms.TABLE_NAME + " (" +
                    alarms._ID + " INTEGER PRIMARY KEY," +
                    alarms.COLUMN_NAME_HOUR + " TEXT," +
                    alarms.COLUMN_NAME_MINUTES + " TEXT," +
                    alarms.COLUMN_NAME_CHECKED + " TEXT,"+
                     alarms.COLUMN_NAME_DAY + " TEXT)";
    public static class alarms implements BaseColumns{
        public static final String TABLE_NAME = "alarms";
        public static final String COLUMN_NAME_HOUR = "hour";
        public static final String COLUMN_NAME_MINUTES = "minute";
        public static final String COLUMN_NAME_DAY = "day";
        public static final String COLUMN_NAME_CHECKED = "checked";
    }
}
