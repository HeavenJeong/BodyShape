package com.example.haewonjeong.bs;

import android.app.ActionBar;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class ManboActivity extends Activity implements SensorEventListener {

    public static int cnt , dis ,cal  = 0;

    SQLiteDatabase db;
    DBHelper helper;


    private TextView stepView;
    private TextView distanceView;
    private TextView calorieView;
    private Button resetBtn;

    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;
    private float x, y, z;
    String weight;
    String height;
    private static final int SHAKE_THRESHOLD = 3000;
    private static final int DATA_X = SensorManager.DATA_X;
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;

    private SensorManager sensorManager;
    private Sensor accelerormeterSensor;
    private CharSequence mTitle;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.customtitlebar);
        setContentView(R.layout.activity_manbo);


        helper = new DBHelper(ManboActivity.this);
        db=helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM user_info", null);
        c.moveToLast();
        c.moveToPrevious();


        while (c.moveToNext()) {
            String name = c.getString(c.getColumnIndex("weight"));
            height = c.getString(c.getColumnIndex("height"));
            weight = c.getString(c.getColumnIndex("goal_weight"));
            String sex = c.getString(c.getColumnIndex("sex"));
            if(sex.equals("f"))
            {
                sex = "여성";
            }
            else if(sex.equals("m"))
            {
                sex = "남성";
            }

            Log.i("asdf", height);

        }

        mTitle = getTitle();
        mTitle = getString(R.string.title_section3);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        stepView = (TextView) findViewById(R.id.cntView);
        distanceView = (TextView) findViewById(R.id.disView);
        calorieView = (TextView) findViewById(R.id.calView);
        resetBtn = (Button) findViewById(R.id.resetBtn);

        stepView.setText("" + cnt);
        distanceView.setText("" + dis);
        calorieView.setText("" + cal);

    }
    @Override
    public void onStart() {
        super.onStart();
        if (accelerormeterSensor != null)
            sensorManager.registerListener(this, accelerormeterSensor,
                    SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (sensorManager != null)
            sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            long gabOfTime = (currentTime - lastTime);
            if (gabOfTime > 100) {
                lastTime = currentTime;
                x = event.values[SensorManager.DATA_X];
                y = event.values[SensorManager.DATA_Y];
                z = event.values[SensorManager.DATA_Z];


                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    stepView.setText("" + (++cnt));
                   /* String iv = String.format("%.1f",cnt*0.8);
                    distanceView.setText("" + iv);
                    String av = String.format("%.2f",cnt*0.06);
                    calorieView.setText("" + av);*/
                    int weight2 = Integer.parseInt(weight);
                    int height2 = Integer.parseInt(height);
                    String av = String.format("%.1f",cnt*(weight2 * 0.0009));
                    calorieView.setText("" + av);
                    String iv = String.format("%.1f",cnt*(height2 * 0.006));
                    distanceView.setText("" + iv);
                }
                lastX = event.values[DATA_X];
                lastY = event.values[DATA_Y];
                lastZ = event.values[DATA_Z];
            }

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.resetBtn :
                cnt = 0;
                dis = 0;
                cal = 0;
                stepView.setText("" + cnt);
                distanceView.setText("" + dis);
                calorieView.setText("" + cal);

                break;
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
