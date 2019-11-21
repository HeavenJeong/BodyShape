package com.example.haewonjeong.bs;


import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


/*네비드러오의 표현을 위한 메소드들을 관리하는 프래그먼트
* */
public class NavigationDrawerFragment extends Fragment {


     // 선택된 아이템의 포지션을 저장
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    private NavigationDrawerCallbacks mCallbacks; //현재 콜백 인스턴스(엑티비티)를 가르키는 포인터
    //네비드로어의 액션을 관리하는 컴포넌트
    /*이제 액티비티 본체를 작성할 차례입니다.
    먼저, 네비게이션 드로어 구현에 필요한 요소들과
    위에서 만든 두 프래그먼트(TextFragment, ImageFragment)의 인스턴스를 선언합니다.*/
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

    public NavigationDrawerFragment() {
    }

    /*다음, onCreate() 메서드에서 각 요소들을 초기화합니다.
    먼저, 두 프래그먼트를 생성한 후, 액티비티 최초 실행시 TextFragment가 표시되도록 구현하였습니다.*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        //defult 아이템(0번) 혹은 마지막으로 선택된 아이템을 선택할지 고름
        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 액션바 (맨위 상단부)에서, 프래그먼트가 액션에 영향을줄지
        setHasOptionsMenu(true);
    }
/*네비게이션 드로어에서 표시할 메뉴 항목과, 각 항목을 선택했을 때 선택한 프래그먼트를 화면에 표시합니다.
*/
    /*add, remove : 최초 프래그먼트를 추가할 때에는 add 함수가 유일하다. 하지만 가변적으로 추가된 프래그먼트를 교체할 때에는 replace(혹은 remove + add) 함수를 써야 하는데 이때 고려해야 할 것이 있다. remove 함수는 프래그먼트 객체 자체가 제거된다는 점이다. 따라서 차후 다시 제거된 프래그먼트를 보여주려면 새로운 프래그먼트를 생성해야 한다. 그리고 새로 생성된 프래그먼트는 제거된 프래그먼트의 값들을 유지할 수 없다. 만일 프래그먼트의 값을 유지할 필요가 없고, 늘 새로운 내용을 반영해야 하는 경우라면 replace(혹은 remove + add)  함수를 사용하자.

attach, detach : 이 함수들은 추가된 프래그먼트의 레이아웃만 제거하거나 다시 추가한다. 따라서 레이아웃이 제거되었다 다시 보여질 때, 레이아웃 변경이 필요한 경우 사용되는 것이 좋다. 그리고 remove, add와 달리 이 함수들은 프래그먼트 객체 자체가 제거되지 않기 때문에 프래그먼트 자체에 값들은 유지된다.

show, hide : 이 함수는 프래그먼트나 프래그먼트가 가진 레이아웃을 제거하고 새로 추가하지 않는다. 순수하게 레이아웃만 숨기거나 보여주기 때문이다. 따라서 프래그먼트와 레이아웃의 설정은 그대로 재사용할 수 있다. 또한 제거 및 다시 생성 과정이 전혀 없기 때문에 시스템 부하가 거의 없다. 하지만 프래그먼트가 보여질 때 전혀 다른 내용을 반영해야 한다면 replace 혹은 attach, detach를 사용해야 할 것이다.

*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDrawerListView = (ListView) inflater.inflate(
                R.layout.fragment_navigation_drawer, container, false);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
        mDrawerListView.setAdapter(new ArrayAdapter<String>(
                getActionBar().getThemedContext(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                new String[]{
                        getString(R.string.title_section1),
                        getString(R.string.title_section2),
                        getString(R.string.title_section3),
                        getString(R.string.title_section4),
                        getString(R.string.title_section5),
                        getString(R.string.title_section6),
                        getString(R.string.title_section7)
                }));
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
        return mDrawerListView;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * @param fragmentId  액티비티의 레이아웃에서 이 프래그먼트의 아이티
     * @param drawerLayout . 이 UI 프래그먼트를 담고있는 드로어 레이아웃
     */
    //프래그먼트가 네비게이션 드로어를 셋업하기위한 메소드를 호출하는것을 관리
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        //드로어가 오픈될때, shadow 커스텀(메인 content를 덮고있는) 을 관리
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // 드로어의 리스트뷰 아이템들과 , 클릭리스너 관리
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);


        //액션바토글은, 액션바의 아이콘과, 네비게이션 드로어간의 동작 관리
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //새 드로어 토글 component가 있는지 확인
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //드로어가 열리면, 액션바 (맨위 상단부) global app action를 보여주고 관리.
        //또한 showGlobalContextActionBar를 보여줌.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    //맨위 상단부 버튼들
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            //Toast.makeText(getActivity(), "Nevigation Btn", Toast.LENGTH_SHORT).show();
            return true;
        }

        /*if (item.getItemId() == R.id.action_example) {
            Toast.makeText(getActivity(), "My Status", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.action_info) {
            Toast.makeText(getActivity(), "Info", Toast.LENGTH_SHORT).show();
            return true;
        }*/


        return super.onOptionsItemSelected(item);
    }

    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return getActivity().getActionBar();
    }

    /**
     * 프래그먼트가 꼭 실행해야하는 Callbacks interface
     */
    public static interface NavigationDrawerCallbacks {
        //네비게이션 드로어의 아이템이 선택될때 호출됨
        void onNavigationDrawerItemSelected(int position);
    }
}
