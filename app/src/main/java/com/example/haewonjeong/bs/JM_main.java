package com.example.haewonjeong.bs;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class JM_main extends Activity {

    Button jm_btn_camera;
    Button jm_btn_overlap;
    Button jm_btn_slide;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jm_main);

        mTitle = getTitle();
        mTitle = getString(R.string.title_section2);

        jm_btn_camera = (Button)findViewById(R.id.jm_btn_camera);
        jm_btn_overlap = (Button)findViewById(R.id.jm_btn_overlap);
        jm_btn_slide = (Button)findViewById(R.id.jm_btn_slide);
        buttonaction();
    }

    public void buttonaction(){
        jm_btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jm_intent = new Intent(getApplicationContext(), JM_camera.class);
                startActivity(jm_intent);

            }
        });
        jm_btn_overlap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jm_intent = new Intent(getApplicationContext(), JM_customergallery.class);
                jm_intent.putExtra("jm",200);
                startActivity(jm_intent);
            }
        });
        jm_btn_slide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jm_intent = new Intent(getApplicationContext(), JM_customergallery.class);
                jm_intent.putExtra("jm", 100);
                startActivity(jm_intent);
            }
        });

    }
    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_jm_main, menu);
        restoreActionBar();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
