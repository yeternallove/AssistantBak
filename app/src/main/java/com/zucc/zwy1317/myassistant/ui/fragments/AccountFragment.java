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
 * @date: 2017/7/5 13:40
 */

public class AccountFragment extends BaseFragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater , @Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
//        View view = inflater.inflate(R.layout.ftagment_account,container,false);
        View view = View.inflate(getContext(),R.layout.ftagment_account,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
        }
    }
}
