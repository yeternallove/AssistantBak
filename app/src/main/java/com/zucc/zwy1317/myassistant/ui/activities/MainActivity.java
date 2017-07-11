package com.zucc.zwy1317.myassistant.ui.activities;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.ui.base.*;
import com.zucc.zwy1317.myassistant.ui.fragments.AccountFragment;
import com.zucc.zwy1317.myassistant.ui.fragments.ScheduleFragment;
import com.zucc.zwy1317.myassistant.util.CalendarManager;
import com.zucc.zwy1317.myassistant.util.LocationHelper;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final static  String[] TAG_FRAGMENT = new String[]{"ACC","SCH"};

    private String TAG_NOW;
    private FragmentManager fm;
    @BindView(R.id.main_appbarlayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        initCalendarInfo();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.main_frameLayout_fragment,new AccountFragment(),TAG_FRAGMENT[0])
                .commit();
        TAG_NOW = TAG_FRAGMENT[0];

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        appBarLayout.setVisibility(View.GONE);
        switch (id){
            case R.id.nav_camera:
                break;
            case R.id.nav_gallery:
                switchFragment(TAG_FRAGMENT[0],new AccountFragment());
                break;
            case R.id.nav_slideshow:
                switchFragment(TAG_FRAGMENT[1],new ScheduleFragment());
                break;
            case R.id.nav_manage:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;
            default:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initCalendarInfo() {
        // 设置日历显示的时间，最大为当前时间+1年，最小为当前时间-2月
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -10);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);
        //根据你传入的开始结束值，构建生成Calendar数据（各种Item，JavaBean）
        CalendarManager.getInstance(this).buildCal(minDate, maxDate, Locale.getDefault());
    }

    private void switchFragment(String tag,BaseFragment bf){
        BaseFragment fragment ;
        FragmentTransaction ft = fm.beginTransaction();
        fragment = (BaseFragment) fm.findFragmentByTag(TAG_NOW);
        if(fragment !=null && fragment.isAdded()){
            ft.hide(fragment);
        }
        fragment = (BaseFragment) fm.findFragmentByTag(tag);
        TAG_NOW = tag;
        if(fragment == null ){
            ft.add(R.id.main_frameLayout_fragment,bf,tag);
        }else {
            ft.show(fragment);
        }
        ft.commit();
    }
}
