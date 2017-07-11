package com.zucc.zwy1317.myassistant.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.ui.activities.AccountAddActivity;
import com.zucc.zwy1317.myassistant.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/5 13:40
 */

public class AccountFragment extends BaseFragment implements View.OnClickListener{
    @BindView(R.id.acc_imgBtn_account)
    ImageButton imgBtnAccount;
    @Override
    public View onCreateView(LayoutInflater inflater , @Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_account,container,false);
//        View view = View.inflate(getContext(),R.layout.fragment_account,null);
        ButterKnife.bind(this,view);

        imgBtnAccount.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.acc_imgBtn_account:
                AccountAddActivity.actionStart(getActivity());
                break;
        }
    }
}
