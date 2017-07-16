package com.zucc.zwy1317.myassistant.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.ui.activities.MainActivity;
import com.zucc.zwy1317.myassistant.ui.base.BaseActivity;
import com.zucc.zwy1317.myassistant.ui.base.BaseFragment;
import com.zucc.zwy1317.myassistant.util.UserManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/12 09:05
 */

public class OtherFragment extends BaseFragment implements View.OnClickListener{

    private MainActivity activity;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    @BindView(R.id.me_toolbar)
    Toolbar toolbar;
    @BindView(R.id.other_swt_me_night)
    Switch swtNight;
    public View onCreateView(LayoutInflater inflater , @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_other,container,false);
//        View view = View.inflate(getContext(),R.layout.fragment_account,null);
        ButterKnife.bind(this,view);
        activity = (MainActivity)getActivity();
        activity.onFragmentHiddenChanged(false,MainActivity.TAG_ME);

        drawer = activity.getDrawer();
        toggle = new ActionBarDrawerToggle(
                activity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        if(UserManager.getThemeNow() == UserManager.THEME_NIGHT){
            swtNight.setChecked(true);
        }
        swtNight.setOnClickListener(this);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        activity.onFragmentHiddenChanged(hidden,MainActivity.TAG_ME);
        if (!hidden){
            drawer.setDrawerListener(toggle);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.other_swt_me_night:
                if(swtNight.isChecked()){
                    UserManager.setThemeNow(UserManager.THEME_NIGHT);
                    activity.setTheme(R.style.NightTheme);
                }else{
                    UserManager.setThemeNow(UserManager.THEME_DAY);
                    activity.setTheme(R.style.AppTheme);
                }
                break;
            default:
                break;
        }
    }
}
