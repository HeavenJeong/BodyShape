package com.example.haewonjeong.bs;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Food_day extends Activity {


    private CharSequence mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_day);
        Button bt = (Button)findViewById(R.id.confirm);
        mTitle ="FOOD";
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),History_cal.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public void go_morning(View v)
    {

        Intent intent = new Intent(this,FoodActivity.class);
        intent.putExtra("Eat",1);
        startActivity(intent);
        finish();
    }
    public void go_lunch(View v)
    {

        Intent intent = new Intent(this,FoodActivity2.class);
        intent.putExtra("Eat",2);
        startActivity(intent);
        finish();
    }
    public void go_dinner(View v)
    {

        Intent intent = new Intent(this,FoodActivity3.class);
        intent.putExtra("Eat",3);
        startActivity(intent);
        finish();
    }


    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food_day, menu);
        restoreActionBar();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
