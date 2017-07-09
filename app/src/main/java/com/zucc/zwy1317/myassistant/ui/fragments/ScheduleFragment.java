package com.zucc.zwy1317.myassistant.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.ui.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/4 14:27
 */

public class ScheduleFragment extends BaseFragment implements View.OnClickListener{
    @Override
    public View onCreateView(LayoutInflater inflater , @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
//        View view = inflater.inflate(R.layout.fragment_account,container,false);
        View view = View.inflate(getContext(), R.layout.fragment_schedule,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
        }
    }
}
