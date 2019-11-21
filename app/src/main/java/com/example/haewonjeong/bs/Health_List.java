/*
    HealthActivity에서 운동종류(큰 틀) 골랐을때
    (ex 팔운동, 다리운동, etc.)
*/
package com.example.haewonjeong.bs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/*

"Whole Body Exercise","Arm Exercise", "Leg Exercise", "Abdominal Exercise","Dorsal Exercise","Weight Loss", "LowerBack Exercise"
   ,"Deltoids Exercise"

*/
public class Health_List extends Activity {
    Intent i = new Intent(Intent.ACTION_VIEW);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health__list);

        Bundle bundle = getIntent().getExtras();
        String text = bundle.getString("arr_text");

        if(text.equals("Whole Body Exercise"))
        {
            Uri u = Uri.parse("http://tvcast.naver.com/v/259629");
            i.setData(u);
            startActivity(i);
        }
        else if(text.equals("Arm Exercise"))
        {
            Uri u = Uri.parse("http://terms.naver.com/entry.nhn?docId=2403774&cid=51031&categoryId=51031");
            i.setData(u);
            startActivity(i);
        }
        else if(text.equals("Leg Exercise"))
        {
            Uri u = Uri.parse("http://tvcast.naver.com/v/276563");
            i.setData(u);
            startActivity(i);
        }
        else if(text.equals("Abdominal Exercise"))
        {
            Uri u = Uri.parse("http://tvcast.naver.com/v/301327");
            i.setData(u);
            startActivity(i);
        }
        else if(text.equals("Dorsal Exercise"))
        {
            Uri u = Uri.parse("http://tvcast.naver.com/v/185810");
            i.setData(u);
            startActivity(i);
        }
        else if(text.equals("Weight Loss"))
        {
            Uri u = Uri.parse("http://terms.naver.com/entry.nhn?docId=2403633&cid=51031&categoryId=51031");
            i.setData(u);
            startActivity(i);
        }
        else if(text.equals("Deltoids Exercise"))
        {
            Uri u = Uri.parse("http://terms.naver.com/entry.nhn?docId=2403833&cid=51031&categoryId=51031");
            i.setData(u);
            startActivity(i);
        }
        else if(text.equals("LowerBack Exercise"))
        {
            Uri u = Uri.parse("http://terms.naver.com/entry.nhn?docId=2403730&cid=51031&categoryId=51031");
            i.setData(u);
            startActivity(i);
        }
/*
        Intent intent = new Intent(this, HealthActivity.class);
        startActivity(intent);
        finish();
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_health__list, menu);
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
