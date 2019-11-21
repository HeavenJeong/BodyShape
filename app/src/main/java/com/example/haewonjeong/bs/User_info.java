package com.example.haewonjeong.bs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class User_info extends Activity implements RadioGroup.OnCheckedChangeListener {


        SQLiteDatabase db2;
        SQLiteDatabase db;
        MySQLiteOpenHelper helper;
        DBHelper helper2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_userinfo);

        helper2 = new DBHelper(User_info.this);

        final EditText edit1 = (EditText)findViewById(R.id.edit1);
        final EditText edit2 = (EditText)findViewById(R.id.edit2);
        final EditText edit3 = (EditText)findViewById(R.id.edit3);
        final RadioButton rb1 = (RadioButton)findViewById(R.id.rb1);
        final RadioButton rb2 = (RadioButton)findViewById(R.id.rb2);


        Button bt2 = (Button)findViewById(R.id.button5);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db2=helper2.getReadableDatabase();
                Cursor c = db2.rawQuery("SELECT * FROM user_info", null);
                c.moveToLast();
                c.moveToPrevious();

                while (c.moveToNext()) {
                    String name = c.getString(c.getColumnIndex("weight"));
                    String height = c.getString(c.getColumnIndex("height"));
                    String weight = c.getString(c.getColumnIndex("goal_weight"));
                    String sex = c.getString(c.getColumnIndex("sex"));
                    if(sex.equals("f"))
                    {
                        sex = "여성";
                    }
                    else if(sex.equals("m"))
                    {
                        sex = "남성";
                    }
                    Toast.makeText(getApplicationContext(),"몸무게 : "+name,Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"키 : "+height,Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"목표몸무게: "+weight,Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"성별 "+sex,Toast.LENGTH_SHORT).show();
                    }

            }
        });

       Button bt = (Button)findViewById(R.id.button4);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // int r = radio.getCheckedRadioButtonId();
                db = helper2.getWritableDatabase();

                ContentValues values= new ContentValues();


                String sex = "";
                if(rb1.isChecked())
                {
                    sex = "m";
                }
                else if(rb2.isChecked())
                {
                    sex ="f";
                }


                Editable height = edit1.getText();
                Editable weight = edit2.getText();
                Editable goal = edit3.getText();
                String h =  height.toString();
                String w =  weight.toString();
                String g =  goal.toString();



                values.put("weight",w);
                values.put("height",h);
                values.put("goal_weight", g);
                values.put("sex",sex);


                db.insert("user_info", null, values);


                Toast.makeText(getApplication(),"사용자 정보가 저장되었습니다", Toast.LENGTH_LONG).show();

               // db.close();


                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jm_main, menu);
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }
}
