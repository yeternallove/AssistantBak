package com.zucc.zwy1317.myassistant.ui.activities;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.db.AssistantDB;
import com.zucc.zwy1317.myassistant.ui.base.BaseActivity;
import com.zucc.zwy1317.myassistant.ui.base.BaseFragment;
import com.zucc.zwy1317.myassistant.ui.fragments.AccountFragment;
import com.zucc.zwy1317.myassistant.ui.fragments.ChatFragment;
import com.zucc.zwy1317.myassistant.ui.fragments.OtherFragment;
import com.zucc.zwy1317.myassistant.ui.fragments.ScheduleFragment;
import com.zucc.zwy1317.myassistant.util.CalendarManager;
import com.zucc.zwy1317.myassistant.util.UserManager;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public final static  String[] TAG_FRAGMENT = new String[]{"CHAT","ACC","SCH","ME"};
    public final static int TAG_CHAT = 0;
    public final static int TAG_ACC = 1;
    public final static int TAG_SCH = 2;
    public final static int TAG_ME = 3;

    private String TAG_NOW;
    private FragmentManager fm;
    private ActionBarDrawerToggle toggle;
    private InputMethodManager imm;

    @BindView(R.id.main_appbarlayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.main_appbar_tv_time)
    TextView tvTime;
    @BindView(R.id.main_appbar_img_toggle)
    ImageView imgToggle;
    @BindView(R.id.main_appbar_acc_tv_title)
    TextView tvTitle;
    @BindView(R.id.main_appbar_img_today)
    ImageView imgToday;

    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        initCalendarInfo();

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        fm = getSupportFragmentManager();

        fm.beginTransaction()
                .add(R.id.main_frameLayout_fragment,new ChatFragment(),TAG_FRAGMENT[0])
                .commit();
        TAG_NOW = TAG_FRAGMENT[TAG_CHAT];
        navigationView.setCheckedItem(R.id.nav_chat);

        navigationView.setNavigationItemSelectedListener(this);
        test();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
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
        switch (id){
            case R.id.nav_chat:
                switchFragment(TAG_NOW,TAG_FRAGMENT[TAG_CHAT],new ChatFragment());
                break;
            case R.id.nav_account:
                switchFragment(TAG_NOW,TAG_FRAGMENT[TAG_ACC],new AccountFragment());
                break;
            case R.id.nav_schedule:
                switchFragment(TAG_NOW,TAG_FRAGMENT[TAG_SCH],new ScheduleFragment());
                break;
            case R.id.nav_other:
                switchFragment(TAG_NOW,TAG_FRAGMENT[TAG_ME],new OtherFragment());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment bf = fm.findFragmentByTag(TAG_FRAGMENT[1]);
        if(bf != null) {
            bf.onActivityResult(requestCode, resultCode, data);
        }
        bf = fm.findFragmentByTag(TAG_FRAGMENT[2]);
        if(bf != null) {
            bf.onActivityResult(requestCode, resultCode, data);
        }
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

    private void switchFragment(String tagNow,String tagChanged,BaseFragment bf){
        BaseFragment fragment ;
        FragmentTransaction ft = fm.beginTransaction();
        fragment = (BaseFragment) fm.findFragmentByTag(tagNow);
        if(fragment !=null && fragment.isAdded()){
            ft.hide(fragment);
        }
        fragment = (BaseFragment) fm.findFragmentByTag(tagChanged);
        if(fragment == null ){
            ft.add(R.id.main_frameLayout_fragment,bf,tagChanged);
        }else {
            ft.show(fragment);
        }
        ft.commit();
        this.TAG_NOW = tagChanged;
    }
    //界面切换 修改toolbar内容
    public void onFragmentHiddenChanged(boolean is,int type){
        switch (type){
            case 0:
                if(is){
                    toolbar.setTitle("");
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),0);
                }else{
                    toolbar.setTitle("晓梦");
                }
                break;
            case 1:
                if(is){
                    tvTitle.setVisibility(View.GONE);
                }else{
                    tvTitle.setVisibility(View.VISIBLE);
                }
                break;
            case 2:
                if(is){
                    tvTime.setVisibility(View.GONE);
                    imgToggle.setVisibility(View.GONE);
                    imgToday.setVisibility(View.GONE);
                }else{
                    tvTime.setVisibility(View.VISIBLE);
                    imgToggle.setVisibility(View.VISIBLE);
                    imgToday.setVisibility(View.VISIBLE);
                }
                break;
            case 3:
                if(is){
                    appBarLayout.setVisibility(View.VISIBLE);
                    drawer.setDrawerListener(toggle);
                }else{
                    appBarLayout.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }

    public ImageView getImgToday(){
        return  this.imgToday;
    }

    public DrawerLayout getDrawer(){
        return this.drawer;
    }

    //测试使用
    private void test(){
        AssistantDB assistantDB = AssistantDB.getInstance(this);
        assistantDB.initTypeIcon();
        UserManager.getInstance(this);
    }
}
