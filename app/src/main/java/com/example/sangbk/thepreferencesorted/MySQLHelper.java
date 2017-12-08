package com.example.sangbk.thepreferencesorted;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sangbk on 10/18/2017.
 */

public class MySQLHelper extends SQLiteOpenHelper {
    public MySQLHelper(Context context) {
        super(context,"ManageRestaurant",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE listRestaurant(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT , type TEXT, discount TEXT, notes TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insert(String name,String address, String type,String discount, String notes){
        ContentValues cv=new ContentValues();
        cv.put("name",name);
        cv.put("address",address);
        cv.put("type",type);
        cv.put("discount",discount);
        cv.put("notes",notes);
        getReadableDatabase().insert("listRestaurant","name",cv);
    }
    public Cursor getAll(){
        return getReadableDatabase().rawQuery("SELECT * FROM listRestaurant ORDER BY name",null);
    }
    public String getName(Cursor c){
        return  c.getString(1);
    }
    public String getAddress(Cursor c){
        return  c.getString(2);
    }
    public String getType(Cursor c){
        return  c.getString(3);
    }
    public String getDiscount(Cursor c){
        return  c.getString(4);
    }
    public String getNotes(Cursor c){
        return  c.getString(5);
    }

}
