package com.example.haewonjeong.bs;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class FoodActivity extends Activity implements AdapterView.OnItemClickListener{



    SQLiteDatabase db;
    MySQLiteOpenHelper helper;
    FoodHelper helper2;
    private Context mContext;
    ArrayList<CustomListInfo> mOrders;
    CustomListAdapter mAdapter;
    ListView mList;
    int kcal;
    boolean ic;
    Now_Kal k;
    TextView now_kcal;
    TextView day_kcal;
    String fn = "food.txt";
    String food[] = new String[400];
    private CharSequence mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        mTitle = "Your Food List";
        helper = new MySQLiteOpenHelper(FoodActivity.this);

        day_kcal = (TextView)findViewById(R.id.day_kc);
        now_kcal = (TextView)findViewById(R.id.now_kc);
        k = new Now_Kal();
        kcal = k.get_Kcal();
        now_kcal.setText(kcal+"Kcal");
        mList = (ListView)findViewById(R.id.food_listView);

        mOrders = new ArrayList<CustomListInfo>();


        mOrders.clear();
        try {
            select();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mAdapter = new CustomListAdapter(this, R.layout.listitem,mOrders);
        mList.setAdapter(mAdapter);


        mList.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CustomListInfo i = mOrders.get(position);
        boolean b = i.getCheck();
        kcal = k.get_Kcal();
        if(b == true)
        {
            i.setCheck(false);
            int temp = Integer.parseInt(i.getKcal());
            temp = kcal - temp;
            k.set_Kcal(temp);
            now_kcal.setText(temp+"Kcal");
        }
        else
        {
            i.setCheck(true);
            int temp = Integer.parseInt(i.getKcal());
            temp += kcal;
            k.set_Kcal(temp);
            now_kcal.setText(temp+"Kcal");
        }

        mOrders.set(position, i);
        mAdapter = new CustomListAdapter(this, R.layout.listitem,mOrders);
        mList.setAdapter(mAdapter);

    }
    //?좎떙?쇱삕 ?좎룞?숉떚?좎룞?숉떚?좎룞???좎떛?몄삕
    public void go_search(View v)
    {
        Intent i = new Intent(getApplicationContext(), Food_Search.class);
        startActivity(i);
        finish();
    }

    public void go_select(View v)
    {
        int temp = k.get_Kcal();    //"temp" value go to save in DB with Y/M/D/(M,L,D)
        helper2 = new FoodHelper(FoodActivity.this);
        db = helper2.getWritableDatabase();
        ContentValues val = new ContentValues();

        SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
        Date day =  new Date();
        String mTime = date.format(day);
        String eat = (String) now_kcal.getText();


        val.put("date",mTime);
        val.put("cal",eat);
        val.put("kind","1");

        db.insert("food_record",null,val);
        Toast.makeText(getApplicationContext(),"칼로리 정보가 저장되었습니다",Toast.LENGTH_SHORT).show();


        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    public void select() throws IOException {
        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM food", null);

        int i=1;
        InputStream in = getResources().openRawResource(R.raw.food);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in,"EUC_KR"));
        while (c.moveToNext()) {

            //    int _id = c.getInt(c.getColumnIndex("_id"));
            //String name = c.getString(c.getColumnIndex("food_name"));
            String name = null;
            try {
                name = reader.readLine();
                StringTokenizer token = new StringTokenizer(name,"|");
                food[i]=token.nextToken();
            } catch (IOException e) {
                e.printStackTrace();
            }


            int age = c.getInt(c.getColumnIndex("cal"));
            String address = c.getString(c.getColumnIndex("category"));

            String key = c.getString(c.getColumnIndex("primarykey"));

            String resname= "a_"+i;

            String pack = this.getPackageName();
            int resid = getResources().getIdentifier(resname, "drawable", pack);


            mOrders.add(new CustomListInfo(false, resid ,food[i] , Integer.toString(age)));
            i++;
        }

    }


    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("mTitle");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_back) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
