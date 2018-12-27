package com.abhijit.alarmclock.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.abhijit.alarmclock.Model.alarmModel;

import java.util.ArrayList;
import java.util.List;

public class databaseHandler  {

    private Context mContext;
    public databaseHandler(Context c){
        mContext=c;
    }
    public int addAlarmToDatabase(alarmModel a){
        databaseHelper mDatabaseHelper = new databaseHelper(mContext);
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(alarmContract.alarms.COLUMN_NAME_HOUR,a.getHour());
        values.put(alarmContract.alarms.COLUMN_NAME_MINUTES,a.getMinutes());
        values.put(alarmContract.alarms.COLUMN_NAME_CHECKED,a.isOn());
        values.put(alarmContract.alarms.COLUMN_NAME_DAY,a.getDayOfmonth());
        long newRow =db.insert(alarmContract.alarms.TABLE_NAME,null,values);
        int id = (int)newRow;
        return id;
    }

    public void updateOnStop(boolean toggle,int id){
        databaseHelper mDatabaseHelper = new databaseHelper(mContext);
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();


        ContentValues values =new ContentValues();
        values.put(alarmContract.alarms.COLUMN_NAME_CHECKED,toggle);
        String selection = alarmContract.alarms._ID + " LIKE ?";
        String[] selectionArgs = { Integer.toString(id)};
        int count = db.update(
                alarmContract.alarms.TABLE_NAME,
                values,
                selection,
                selectionArgs);

    }

    public void updateDatabase(boolean toggle,String day,int id){
        databaseHelper mDatabaseHelper = new databaseHelper(mContext);
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();


        ContentValues values =new ContentValues();
        values.put(alarmContract.alarms.COLUMN_NAME_CHECKED,toggle);
        values.put(alarmContract.alarms.COLUMN_NAME_DAY,day);
        String selection = alarmContract.alarms._ID + " LIKE ?";
        String[] selectionArgs = { Integer.toString(id)};
        int count = db.update(
               alarmContract.alarms.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public List<alarmModel> getAlarmsFromDatabase(){
        List<alarmModel> alarmModels = new ArrayList<>();
        databaseHelper mDatabaseHelper = new databaseHelper(mContext);
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ alarmContract.alarms.TABLE_NAME, null);
        if (c.moveToFirst()){
            do {
                // Passing values
                String column1 = c.getString(0);
                String column2 = c.getString(1);
                String column3 = c.getString(2);
                String column4 = c.getString(3);
                String column5 = c.getString(4);
                int hours = Integer.parseInt(column2);
                int minutes = Integer.parseInt(column3);
                int day = Integer.parseInt(column5);
                int id= Integer.parseInt(column1);
                boolean on=false;
                if(column4.equals("1")){
                    on = true;
                }else if(column4.equals("0")){
                        on = false;
                }
                alarmModel a= new alarmModel(hours,minutes,on);
                a.setId(id);
                alarmModels.add(a);
                a.setDayOfmonth(day);
                // Do something Here with values
            } while(c.moveToNext());
        }
        c.close();
        db.close();

        return alarmModels;
    }

    public void deleteAlarm(alarmModel a){
        databaseHelper mDatabaseHelper = new databaseHelper(mContext);
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        String selection = alarmContract.alarms._ID + " LIKE ?";
        String[] selectionArgs = { Integer.toString(a.getId()) };
        int deletedRows = db.delete(alarmContract.alarms.TABLE_NAME, selection, selectionArgs);
    }




}
