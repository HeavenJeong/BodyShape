
package com.example.haewonjeong.bs;
/*2 Button*/
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ks_MainActivity extends Activity {

    SQLiteDatabase db;
    MySQLiteOpenHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ks);

        helper = new MySQLiteOpenHelper(ks_MainActivity.this);

    }
    public void insert(String name ,int age, String address)
    {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("food_name", name);
        values.put("cal", age);
        values.put("category", address);
        db.insert("food", null, values);
    }

    public void update(String name, int age)
    {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cal", age);
        db.update("food", values, "food_name=?", new String[]{name});

    }
    public void delete(String name)
    {
        db = helper.getWritableDatabase();
        db.delete("food", "food_name=?", new String[]{name});
        Log.i("db", name + "정상적으로 삭제 되었습니다.");
    }

    public void select()
    {
        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT food.food_name, food.cal, food.category FROM food",null);
      //  Cursor c = db.query("food",null,null,null,null,null,null);
      //      Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'",null);
        while(c.moveToNext())
        {
        //    int _id = c.getInt(c.getColumnIndex("_id"));
            String name = c.getString(c.getColumnIndex("food_name"));
            int age = c.getInt(c.getColumnIndex("cal"));
            String address = c.getString(c.getColumnIndex("category"));

            Log.i("db"," food_name :" +name+ ", cal : "+age+", category : "+address);
        }

/*
        if(c.moveToFirst())
        {
            while(true)
            {
                Log.i("JinSub","Table name : "+c.getString(0));

                if(!c.moveToNext())
                {
                    break;
                }

            }
        }
*/

    }
    /*
    public void insert(String name ,int age, String address)
    {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("age", age);
        values.put("address", address);
        db.insert("student", null, values);
    }

    public void update(String name, int age)
    {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("age",age);
        db.update("student", values, "name=?", new String[]{name});

    }

    public void delete(String name)
    {
        db = helper.getWritableDatabase();
        db.delete("student", "name=?", new String[]{name});
        Log.i("db", name + "정상적으로 삭제 되었습니다.");
    }
*/
 /*    public void select()
    {
        db = helper.getReadableDatabase();
       Cursor c = db.query("student",null,null,null,null,null,null);
    //    Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'",null);

        if(c.moveToFirst())
        {
            while(true)
            {
                Log.i("JinSub","Table name : "+c.getString(0));

                if(!c.moveToNext())
                {
                    break;
                }

            }
        }

        //요건 돼
        while(c.moveToNext())
        {
            int _id = c.getInt(c.getColumnIndex("_id"));
            String name = c.getString(c.getColumnIndex("name"));
            int age = c.getInt(c.getColumnIndex("age"));
            String address = c.getString(c.getColumnIndex("address"));
            Log.i("db","id: "+ _id+", name :" + ", age : "+age+", addr : "+address);
        }

    }
*/
    public void goHealth(View v)
    {
        Intent i = new Intent(this, HealthActivity.class);
        startActivity(i);
    }

    public void goFood(View v)
    {
        Now_Kal i = new Now_Kal();
        i.set_Kcal(0);
        Intent j = new Intent(this, Food_day.class);
        startActivity(j);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
