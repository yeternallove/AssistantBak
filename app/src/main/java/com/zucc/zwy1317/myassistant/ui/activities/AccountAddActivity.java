package com.zucc.zwy1317.myassistant.ui.activities;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.db.AssistantDB;
import com.zucc.zwy1317.myassistant.modle.RecordBean;
import com.zucc.zwy1317.myassistant.modle.TypeIconBean;
import com.zucc.zwy1317.myassistant.ui.base.BaseActivity;
import com.zucc.zwy1317.myassistant.ui.base.BaseFragment;
import com.zucc.zwy1317.myassistant.ui.fragments.TypeIconFragment;
import com.zucc.zwy1317.myassistant.util.CalendarManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/11 10:37
 */

public class AccountAddActivity extends BaseActivity implements View.OnClickListener{

    public static final int ACC_ADD_RESULT_CANCELED= 0;
    public static final int ACC_ADD_SAVE = 1;

    private AssistantDB db;
    private RecordBean recordBean;
    private String uID = "admin";

    private DatePickerDialog mDataPicker;
    private TimePickerDialog mTimePicker;
    private Calendar mCalendar;

    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;

    @BindView(R.id.acc_add_vp)
    ViewPager viewPaper;
    @BindView(R.id.acc_add_tabs)
    TabLayout tabLayout;
    @BindView(R.id.acc_add_toolbar)
    Toolbar toolbar;
    @BindView(R.id.acc_add_btn_save)
    Button btnSave;
    @BindView(R.id.acc_add_edt_money)
    EditText edtMoney;
    @BindView(R.id.acc_add_tv_title)
    TextView tvTitle;
    @BindView(R.id.acc_add_edt_note)
    TextView edtNote;
    @BindView(R.id.acc_add_tv_day_of_mouth)
    TextView tvDay;
    @BindView(R.id.acc_add_tv_time)
    TextView tvTime;
    @BindView(R.id.acc_add_tv_location)
    TextView tvLocation;
    @BindView(R.id.acc_add_img_camera)
    ImageView imgCamera;
    @BindView(R.id.acc_add_img_camera_img)
    ImageView imgCAmeraImg;

    private List<BaseFragment> fragments = new ArrayList<>();
    private String[] tabTiltle;

    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AccountAddActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_add);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(ACC_ADD_RESULT_CANCELED);
                finish();
            }
        });

        fragments.add(new TypeIconFragment());
        fragments.add(new TypeIconFragment());

        tabTiltle = new String[]{this.getResources().getString(R.string.tv_account_income),this.getResources().getString(R.string.tv_account_spending)};

        tabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        tabLayout.addTab(tabLayout.newTab().setText(tabTiltle[0]));//添加tab选项卡
        tabLayout.addTab(tabLayout.newTab().setText(tabTiltle[1]));

        MyPagerAdapter myAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPaper.setAdapter(myAdapter);//给ViewPager设置适配器
        tabLayout.setupWithViewPager(viewPaper);//将TabLayout和ViewPager关联起来。
        tabLayout.setTabsFromPagerAdapter(myAdapter);//给Tabs设置适配器

        dateFormat = CalendarManager.getInstance().getGetDateAndWeekFormat();
        timeFormat = CalendarManager.getInstance().getGetTimeFormat();
        mCalendar = Calendar.getInstance();
        tvDay.setText(dateFormat.format(mCalendar.getTime()));
        tvTime.setText(timeFormat.format(mCalendar.getTime()));

        recordBean = new RecordBean(uID);
        db = AssistantDB.getInstance(this);

        btnSave.setOnClickListener(this);
        tvDay.setOnClickListener(this);
        tvLocation.setOnClickListener(this);
        tvTime.setOnClickListener(this);
        imgCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.acc_add_btn_save:
                String strMoney = edtMoney.getText().toString();
                double money;
                if(strMoney.equals("")||( money = Double.parseDouble(strMoney)) == 0){
                    Toast.makeText(this,this.getResources().getString(R.string.acc_add_save_failed),Toast.LENGTH_SHORT).show();
                    return;
                }
                recordBean.setmAmount(money);
                recordBean.setTitle(tvTitle.getText().toString());
                recordBean.setmNote(edtNote.getText().toString());
                recordBean.setmTime(mCalendar.getTime().getTime());
                recordBean.setType(TypeIconBean.TYPE_INCOME);
                db.saveRecord(recordBean);
                setResult(ACC_ADD_SAVE);
                finish();
                break;
            case R.id.acc_add_tv_day_of_mouth:
                getDatePickerDialog();
                break;
            case R.id.acc_add_tv_time:
                getTimePickerDialog();
                break;
            case R.id.acc_add_tv_location:
                break;
            case R.id.acc_add_img_camera:
                break;
            default:
                break;
        }
    }
    private void getDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        mDataPicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                mCalendar.set(year, monthOfYear, dayOfMonth);
                tvDay.setText(dateFormat.format(mCalendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        mDataPicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        mDataPicker.show();
    }



    private void getTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCalendar.set(Calendar.MINUTE, minute);
                tvTime.setText(timeFormat.format(mCalendar.getTime()));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        mTimePicker.show();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTiltle[position];
        }
    }


}
