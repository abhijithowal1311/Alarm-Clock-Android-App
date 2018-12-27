package com.abhijit.alarmclock.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "alarmReader.db";
    private static final String SQL_CREATE_ENTRIES =         "CREATE TABLE " + alarmContract.alarms.TABLE_NAME + " (" +
            alarmContract.alarms._ID + " INTEGER PRIMARY KEY," +
            alarmContract.alarms.COLUMN_NAME_HOUR + " TEXT," +
            alarmContract.alarms.COLUMN_NAME_MINUTES + " TEXT," +
            alarmContract.alarms.COLUMN_NAME_CHECKED + " TEXT,"+
            alarmContract.alarms.COLUMN_NAME_DAY + " TEXT)";

    public databaseHelper(Context c){
        super(c,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
