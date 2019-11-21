package com.example.haewonjeong.bs;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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


public class Food_Search extends Activity implements TextWatcher,AdapterView.OnItemClickListener {

    private Button bt;
    private Button bt2;
    SQLiteDatabase db;
    SQLiteDatabase db2;
    MySQLiteOpenHelper helper;
    FoodHelper helper2;
    private Context mContext;
    ArrayList<CustomListInfo> mOrders;
    CustomListInfo info;
    CustomListAdapter mAdapter;
    ListView mList;
    int kcal;
    boolean ic;
    Now_Kal k;  //칼로리 객체
    TextView now_kcal;  //선택 칼로리
    TextView day_kcal;  //하루 권장 칼로리

    int age;
    int img;

    String key;
    String cal;
    //EditText et ;
    private AutoCompleteTextView auto;
     String list[] = new String[400];//{"a_1","a_2","a_3"};
    private  String list2[] = new String[400];
    BufferedReader reader;
    ContentValues values = new ContentValues();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search2);

        now_kcal = (TextView)findViewById(R.id.calview);
        InputStream in = getResources().openRawResource(R.raw.food);
        try {
            reader = new BufferedReader(new InputStreamReader(in,"EUC_KR"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        k = new Now_Kal();
        kcal = k.get_Kcal();
        now_kcal.setText(kcal+" Kcal");

        helper = new MySQLiteOpenHelper(Food_Search.this);
        db2=helper.getReadableDatabase();
        Cursor c2 = db2.rawQuery("SELECT * FROM food",null);
        int i =0;
        while(c2.moveToNext())
        {

            try {
                String name = reader.readLine();
                StringTokenizer tok = new StringTokenizer(name,"|");
                list[i] = tok.nextToken();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String key = c2.getString(c2.getColumnIndex("primarykey"));

            list2[i] = key;
            //Toast.makeText(getApplicationContext(),list[i]+" "+list2[i],Toast.LENGTH_SHORT).show();
            i++;


        }
        bt2 = (Button)findViewById(R.id.confirm);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper2 = new FoodHelper(Food_Search.this);
                db = helper2.getWritableDatabase();
                ContentValues val = new ContentValues();

                SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
                Date day =  new Date();
                String mTime = date.format(day);
                String eat = (String) now_kcal.getText();

                Toast.makeText(getApplicationContext(),eat,Toast.LENGTH_SHORT).show();

                val.put("date",mTime);
                val.put("cal",eat);
                val.put("kind", "1");

                db.insert("food_record", null, val);

                Toast.makeText(getApplicationContext(),"칼로리 정보가 저장되었습니다",Toast.LENGTH_SHORT).show();
                db = helper2.getReadableDatabase();
                Cursor c2 = db.rawQuery("SELECT * FROM food_record",null);
                int i =0;
                c2.moveToFirst();
                while(c2.moveToNext())
                {
                    String a = c2.getString(c2.getColumnIndex("cal"));
                    Toast.makeText(getApplicationContext(),a,Toast.LENGTH_SHORT).show();

                }


            }
        });

        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select();

            }
        });
        auto = (AutoCompleteTextView) findViewById(R.id.auto);
        auto.addTextChangedListener(this);
        auto.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list));


        mList = (ListView)findViewById(R.id.list);
        mOrders = new ArrayList<CustomListInfo>();
        mOrders.clear();



        mList.setOnItemClickListener(this);


    }

    public void select() {
        db = helper.getReadableDatabase();
        //Editable aa = auto.getText();

        Editable aaa;
        aaa=auto.getText();
        String aa=  aaa.toString();


        Cursor c = db.rawQuery("SELECT * FROM food", null);

        int cnt =0;
        while (c.moveToNext()) {


            //name = c.getString(c.getColumnIndex("food_name"));
            String name = list[cnt];
            age = c.getInt(c.getColumnIndex("cal"));
            key = c.getString(c.getColumnIndex("primarykey"));
            cal = String.valueOf(age);

            if(name.contains(aa)) // if(aa.equals(key))
            {
               // Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
                String pack = this.getPackageName();
                int resid = getResources().getIdentifier(key,"drawable",pack);



                mOrders.add(new CustomListInfo(false, resid, name, Integer.toString(age)));
               // Toast.makeText(getApplicationContext(),cal,Toast.LENGTH_LONG).show();

            }

            mAdapter = new CustomListAdapter(this, R.layout.listitem,mOrders);
            mList.setAdapter(mAdapter);
            mList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            cnt++;

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food__search, menu);
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
            //finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        info = mOrders.get(position);
        boolean b = info.getCheck();
        kcal = k.get_Kcal();
        if(b == true)
        {
            info.setCheck(false);
            int temp = Integer.parseInt(info.getKcal());
            temp = kcal - temp;
            k.set_Kcal(temp);
            now_kcal.setText(temp + "Kcal");

        }
        else
        {
            info.setCheck(true);
            int temp = Integer.parseInt(info.getKcal());
            temp += kcal;
            k.set_Kcal(temp);
            now_kcal.setText(temp + "Kcal");
            img = info.getImageName();


        }

        mOrders.set(position, info);
        mAdapter = new CustomListAdapter(this, R.layout.listitem,mOrders);
        mList.setAdapter(mAdapter);

    }
}
