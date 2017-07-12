package com.zucc.zwy1317.myassistant.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.ui.activities.MainActivity;
import com.zucc.zwy1317.myassistant.ui.base.BaseActivity;
import com.zucc.zwy1317.myassistant.ui.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/12 09:05
 */

public class OtherFragment extends BaseFragment{
    private MainActivity activity;
    public View onCreateView(LayoutInflater inflater , @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_other,container,false);
//        View view = View.inflate(getContext(),R.layout.fragment_account,null);
        ButterKnife.bind(this,view);
        activity = (MainActivity)getActivity();
        activity.onFragmentHiddenChanged(false,MainActivity.TAG_ME);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        activity.onFragmentHiddenChanged(hidden,MainActivity.TAG_ME);
    }
}
