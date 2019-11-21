package com.example.haewonjeong.bs;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by samsung on 2015-08-30.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "test.db";
    private static final String PACKAGE_DIR = "/data/data/com.example.haewonjeong.bs/databases";

    public MySQLiteOpenHelper(Context context)
    {
        super(context, PACKAGE_DIR + "/" + DATABASE_NAME, null, 1);
        initialize(context);
    }

    public static void initialize(Context ctx) {

        File folder = new File(PACKAGE_DIR);
        folder.mkdirs();

        File outfile = new File(PACKAGE_DIR + "/" + DATABASE_NAME);
     //   if (outfile.length() <= 0) {
        if(outfile.exists() == true){
            AssetManager assetManager = ctx.getResources().getAssets();
            try {
                InputStream is = assetManager.open("db/"+DATABASE_NAME, AssetManager.ACCESS_BUFFER);
                long filesize = is.available();
                byte [] tempdata = new byte[(int)filesize];
                is.read(tempdata);
                is.close();
                outfile.createNewFile();
                FileOutputStream fo = new FileOutputStream(outfile);
                fo.write(tempdata);
                fo.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onCreate(SQLiteDatabase db) {
      db.execSQL("CREATE TABLE if not exists u_info(weight varchar,height varchar,goal_weight varchar,sex varchar); ");

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
/*
        String sql = "drop table if exists food";
        db.execSQL(sql);
        onCreate(db);
*/
    }
    /*
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table student ("+
                "_id integer primary key autoincrement, "+
                "name text, "+
                "age integer, "+
                "address text);";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "drop table if exists student";
        db.execSQL(sql);

        onCreate(db);

    }*/
}
