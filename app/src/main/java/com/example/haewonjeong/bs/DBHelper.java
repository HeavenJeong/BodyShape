package com.example.haewonjeong.bs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ssu_user on 2015-09-30.
 */
public class DBHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "test2.db";
    private static final int DB_VER = 1;

    public DBHelper(Context context)
    {
        super(context,DB_NAME,null,DB_VER);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table user_info(weight varchar,height varchar,goal_weight varchar,sex varchar);";
        db.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
