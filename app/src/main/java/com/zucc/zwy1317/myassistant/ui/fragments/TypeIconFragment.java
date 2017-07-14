package com.zucc.zwy1317.myassistant.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.modle.TypeIconBean;
import com.zucc.zwy1317.myassistant.ui.adapters.TypeIconAdapter;
import com.zucc.zwy1317.myassistant.ui.base.BaseFragment;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/11 12:00
 */

public class TypeIconFragment extends BaseFragment {

    private TypeIconAdapter adapter;
    private List<TypeIconBean> data;
    private TypeIconAdapter.MyItemClickListener listener;

    public TypeIconFragment(List<TypeIconBean> list, TypeIconAdapter.MyItemClickListener listener){
        this.data = list;
        this.listener = listener;
    }

    @BindView(R.id.typeicon_recyclerview)
    RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater , @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
//        View view = inflater.inflate(R.layout.fragment_account,container,false);
        View view = View.inflate(getContext(),R.layout.fragment_typeicon,null);
        ButterKnife.bind(this,view);
        adapter = new TypeIconAdapter(getActivity(),data,listener);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),5));
        mRecyclerView.setHasFixedSize(true);
        return view;
    }
}
