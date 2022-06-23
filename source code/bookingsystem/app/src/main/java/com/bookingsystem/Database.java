package com.bookingsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "userDatabase";
    public static final int DATABASE_VERSION = 1;
    public static final String USER_TABLE = "userInfo";
    public static final String ROW_NAME= "name";
    public static final String ROW_PHONE = "phone";
    public static final String ROW_PET = "pet";
    public static final String ROW_OPERATION = "operation";
    public static final String ROW_DATE = "date";
    public static final String ROW_TIME = "time";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USER_TABLE + " ("+ROW_NAME+" TEXT, "+ROW_PHONE+" TEXT, "+ROW_PET+" TEXT, "+ROW_OPERATION+" TEXT, "+ROW_DATE+" TEXT, "+ROW_TIME+" TEXT DEFAULT '0' )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ USER_TABLE);
        db.close();
        onCreate(db);
    }

    public void setData(String name, String phone, String pet, String operation, String date, String time)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ROW_NAME, name.trim());
        cv.put(ROW_PHONE, phone.trim());
        cv.put(ROW_PET, pet.trim());
        cv.put(ROW_OPERATION, operation.trim());
        cv.put(ROW_DATE, date.trim());
        cv.put(ROW_TIME, time.trim());
        db.insert(USER_TABLE, null, cv);
        db.close();
    }


    public List<String> getData(String date)
    {
        List<String> table = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT time FROM userInfo WHERE date = '"+date+"' ";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                table.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }

        return table;

    }

    public List<String> getData(String date, String time)
    {
        List<String> table = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM userInfo WHERE date = '"+date+"' AND time = '"+time+"'";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            int len = cursor.getColumnCount();
            do{
                for(int i=0; i<len; i++)
                    table.add(cursor.getString(i));
            }while(cursor.moveToNext());
        }

        return table;

    }

}
