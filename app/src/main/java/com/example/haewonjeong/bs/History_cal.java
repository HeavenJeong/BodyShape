package com.example.haewonjeong.bs;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class History_cal extends Activity {
    SQLiteDatabase db;
    FoodHelper helper;


   ArrayList<CustomListInfo> mOrders;
    CustomListAdapter mAdapter;
    ListView mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_cal);

        helper = new FoodHelper(History_cal.this);

        mList = (ListView)findViewById(R.id.listView);
        mOrders = new ArrayList<CustomListInfo>();
        mOrders.clear();
        mAdapter = new CustomListAdapter(this, R.layout.listitem,mOrders);
        mList.setAdapter(mAdapter);




        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM food_record",null);

        c.moveToPrevious();
        while(c.moveToNext())
        {
            String date = c.getString(c.getColumnIndex("date"));
            String kind = c.getString(c.getColumnIndex("kind"));
            String cal = c.getString(c.getColumnIndex("cal"));
            if(kind.equals("1"))
            {
                kind= "아침";
            }
            else if(kind.equals("2"))
            {
                kind ="점심";
            }
            else if(kind.equals("3"))
            {
                kind="저녁";
            }

            mOrders.add(new CustomListInfo(false,2,date,kind+" "+cal));
            mAdapter = new CustomListAdapter(this, R.layout.listitem,mOrders);
            mList.setAdapter(mAdapter);

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history_cal, menu);
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
