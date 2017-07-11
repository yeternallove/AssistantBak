package com.zucc.zwy1317.myassistant.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/11 12:00
 */

public class TypeIconFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater , @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
//        View view = inflater.inflate(R.layout.fragment_account,container,false);
        View view = View.inflate(getContext(),R.layout.fragment_typeicon,null);
        ButterKnife.bind(this,view);
        return view;
    }
}
