package com.zucc.zwy1317.myassistant.ui.activities;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.ui.base.BaseActivity;
import com.zucc.zwy1317.myassistant.ui.base.BaseFragment;
import com.zucc.zwy1317.myassistant.ui.fragments.TypeIconFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/11 10:37
 */

public class AccountAddActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.acc_add_vp)
    ViewPager viewPaper;
    @BindView(R.id.acc_add_tabs)
    TabLayout tabLayout;

    @BindView(R.id.acc_add_imgbtn_back)
    ImageButton imgBtnBack;
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

        imgBtnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.acc_add_imgbtn_back:
                finish();
                break;
            default:
                break;
        }
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
