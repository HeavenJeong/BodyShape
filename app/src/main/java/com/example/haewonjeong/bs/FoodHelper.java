package com.example.haewonjeong.bs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lod0 on 2015-09-30.
 */
public class FoodHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "test3.db";
    private static final int DB_VER = 1;

    public FoodHelper(Context context)
    {
        super(context,DB_NAME,null,DB_VER);

    }



    public void onCreate(SQLiteDatabase db) {
        String sql = "create table food_record(date varchar ,cal varchar,kind varchar);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
