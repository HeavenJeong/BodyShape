package com.example.haewonjeong.bs;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    SQLiteDatabase db;
    MySQLiteOpenHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new MySQLiteOpenHelper(MainActivity.this);
        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM u_info",null);
        c.moveToFirst();
       if(c.moveToNext())
       {

       }
        else
       {
           Toast.makeText(getApplicationContext(),"사용자 정보가없어 사용자 정보입력 화면으로 이동합니다",Toast.LENGTH_LONG).show();
           Intent i = new Intent(getApplicationContext(),User_info.class);
           startActivity(i);
           finish();
       }
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer); //왼쪽 프레그먼트이름
        mTitle = getTitle();

        // 드로어 set up
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    /*메인버튼이벤트*/
    public void go_camera(View v)
    {
        Intent i1 = new Intent(this, JM_main.class);
        startActivity(i1);
    }
    public void go_manbo(View v)
    {
        Intent i2 = new Intent(this, ManboActivity.class);
        startActivity(i2);
    }
    public void go_food(View v)
    {
        Intent i3 = new Intent(this, Food_day.class);
        startActivity(i3);
    }
    public void go_exercise(View v)
    {
        Intent i4 = new Intent(this, HealthActivity.class);
        startActivity(i4);
    }

    /*드로어 선택 이벤트*/
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        //현재 존재하는 프래그먼트를 교체함으로써 프래그먼트를 추가
        Fragment fragment = PlaceholderFragment.newInstance(position+1);
        FragmentManager fragmentManager = getFragmentManager();

    /*네비게이션 드로어 클릭리스너*/
        switch(position)
        {
            case 0:
                //Toast.makeText(this,"click zero",Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Intent i1 = new Intent(this,JM_main.class);
                startActivity(i1);
                break;
            case 2:
                Intent i2 = new Intent(this,ManboActivity.class);
                startActivity(i2);
                break;
            case 3:
                Intent i3 = new Intent(this,Food_day.class);
                startActivity(i3);
                break;
            case 4:
                Intent i4 = new Intent(this,HealthActivity.class);
                startActivity(i4);
                break;
            case 5:
                Intent i5 = new Intent(this,User_info.class);
                startActivity(i5);
                break;
            case 6:
                infoCustomDialog dialog = new infoCustomDialog(this);
                dialog.show();
                break;
        }
        fragmentManager.beginTransaction()
                //  .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .replace(R.id.container, fragment)
                .commit();



    }

    //선택시 상단부 이름 변경
    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                //mTitle = getString(R.string.title_section2);
                break;
            case 3:
                //mTitle = getString(R.string.title_section3);
                break;
            case 4:
                //mTitle = getString(R.string.title_section4);
                break;
            case 5:
               // mTitle = getString(R.string.title_section5);
                break;
            case 6:
                //mTitle = getString(R.string.title_section6);
                break;
            case 7:
               // mTitle = getString(R.string.title_section7);
                break;
        }
    }
    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    //옵션메뉴
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //옵션메뉴가 최초로 생성될때 만 호출됨
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) { //옵션아이템이 선택될때 호출됨

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

           infoCustomDialog dialog = new infoCustomDialog(this);
            dialog.show();
            return true;
        }
        if (id == R.id.action_example) {
            Intent i = new Intent(this,User_info.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public static class PlaceholderFragment extends Fragment {
        /*
         * 이 프래그먼트의 색션 번호를 표현하는 프래그먼트 함수
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        //새 인스턴스 반환
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
