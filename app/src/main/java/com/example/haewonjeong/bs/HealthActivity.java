/*
     lower classes start with "H_"
*/
package com.example.haewonjeong.bs;


import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class HealthActivity extends Activity implements AdapterView.OnItemClickListener{

    public String[] arrlist = {"Whole Body Exercise","Arm Exercise", "Leg Exercise", "Abdominal Exercise","Dorsal Exercise","Weight Loss", "LowerBack Exercise"
   ,"Deltoids Exercise" };
    private CharSequence mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        mTitle = getTitle();
        mTitle = getString(R.string.title_section5);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning!!")
                .setMessage("동영상 재생으로 인한 데이터가 부과될 수 있습니다.")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        ListView list = (ListView)findViewById(R.id.h_listview);
        ArrayList<H_ListItem> data = new ArrayList<>();
        H_ListItem whole = new H_ListItem(R.drawable.wholebody,arrlist[0]);
        H_ListItem pal = new H_ListItem(R.drawable.arm,arrlist[1]);
        H_ListItem dari = new H_ListItem(R.drawable.leg,arrlist[2]);
        H_ListItem bokbu = new H_ListItem(R.drawable.abdominal,arrlist[3]);
        H_ListItem deung = new H_ListItem(R.drawable.back,arrlist[4]);
        H_ListItem gamryang = new H_ListItem(R.drawable.weight,arrlist[5]);
        H_ListItem huri = new H_ListItem(R.drawable.lowerback,arrlist[6]);
        H_ListItem ohgge = new H_ListItem(R.drawable.deltoids,arrlist[7]);

        data.add(whole);
        data.add(pal);
        data.add(dari);
        data.add(bokbu);
        data.add(deung);
        data.add(gamryang);
        data.add(huri);
        data.add(ohgge);

        H_listAdapter adapter = new H_listAdapter(this, R.layout.health_listitem,data);
        list.setAdapter(adapter);

        list.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String c_list = arrlist[position];
      /*  Intent i = new Intent(this,Health_List.class);
        i.putExtra("arr_text",c_list);
        startActivity(i);*/
        Intent i = new Intent(Intent.ACTION_VIEW);
        if(c_list.equals("Whole Body Exercise"))
        {
            Uri u = Uri.parse("http://tvcast.naver.com/v/259629");
            i.setData(u);
            startActivity(i);
        }
        else if(c_list.equals("Arm Exercise"))
        {
            Uri u = Uri.parse("http://terms.naver.com/entry.nhn?docId=2403774&cid=51031&categoryId=51031");
            i.setData(u);
            startActivity(i);
        }
        else if(c_list.equals("Leg Exercise"))
        {
            Uri u = Uri.parse("http://tvcast.naver.com/v/276563");
            i.setData(u);
            startActivity(i);
        }
        else if(c_list.equals("Abdominal Exercise"))
        {
            Uri u = Uri.parse("http://tvcast.naver.com/v/301327");
            i.setData(u);
            startActivity(i);
        }
        else if(c_list.equals("Dorsal Exercise"))
        {
            Uri u = Uri.parse("http://tvcast.naver.com/v/185810");
            i.setData(u);
            startActivity(i);
        }
        else if(c_list.equals("Weight Loss"))
        {
            Uri u = Uri.parse("http://terms.naver.com/entry.nhn?docId=2403633&cid=51031&categoryId=51031");
            i.setData(u);
            startActivity(i);
        }
        else if(c_list.equals("Deltoids Exercise"))
        {
            Uri u = Uri.parse("http://terms.naver.com/entry.nhn?docId=2403833&cid=51031&categoryId=51031");
            i.setData(u);
            startActivity(i);
        }
        else if(c_list.equals("LowerBack Exercise"))
        {
            Uri u = Uri.parse("http://terms.naver.com/entry.nhn?docId=2403730&cid=51031&categoryId=51031");
            i.setData(u);
            startActivity(i);
        }
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
        getMenuInflater().inflate(R.menu.menu_health, menu);
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
