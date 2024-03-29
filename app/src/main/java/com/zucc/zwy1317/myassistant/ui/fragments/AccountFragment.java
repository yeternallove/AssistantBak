package com.zucc.zwy1317.myassistant.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.db.AssistantDB;
import com.zucc.zwy1317.myassistant.modle.RecordBean;
import com.zucc.zwy1317.myassistant.modle.TypeIconBean;
import com.zucc.zwy1317.myassistant.ui.activities.AccountAddActivity;
import com.zucc.zwy1317.myassistant.ui.activities.MainActivity;
import com.zucc.zwy1317.myassistant.ui.adapters.AccountAdapter;
import com.zucc.zwy1317.myassistant.ui.base.BaseFragment;
import com.zucc.zwy1317.myassistant.util.DateHelper;
import com.zucc.zwy1317.myassistant.util.UserManager;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/5 13:40
 */

public class AccountFragment extends BaseFragment implements View.OnClickListener{

    public static final int REQ_NEW = 1;
    public static final int REQ_LOOK = 2;

    private AssistantDB assistantDB;
    private List<RecordBean> mData;
    private HashMap<String,TypeIconBean> map;
    private AccountAdapter adapter;
    private MainActivity activity;

    @BindView(R.id.acc_imgBtn_account)
    ImageButton imgBtnAccount;
    @BindView(R.id.account_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.acc_tv_incomeMoney)
    TextView tvIncome;
    @BindView(R.id.acc_tv_spendingMoney)
    TextView tvSpending;

    @Override
    public View onCreateView(LayoutInflater inflater , @Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_account,container,false);
//        View view = View.inflate(getContext(),R.layout.fragment_account,null);
        ButterKnife.bind(this,view);
        activity = (MainActivity)getActivity();
        activity.onFragmentHiddenChanged(false,MainActivity.TAG_ACC);

        assistantDB = AssistantDB.getInstance(getActivity());
        onChangeData();
        map = assistantDB.loadTypeIconMap();

        adapter = new AccountAdapter(getActivity(),mData,map);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);

        imgBtnAccount.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.acc_imgBtn_account:
                startActivityForResult(new Intent(activity, AccountAddActivity.class),  REQ_NEW);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_NEW:
                if(resultCode == AccountAddActivity.ACC_ADD_SAVE){
                    onChangeData();
                    adapter.updateData(mData);
                }
                break;
            case REQ_LOOK:
                break;
            default:
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        activity.onFragmentHiddenChanged(hidden,MainActivity.TAG_ACC);
    }

    public void bindData( List<RecordBean> data){
        Context mContext = getActivity();
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        RecordBean recordBean;
        double sum ;
        int i = 0, j =1;
        int length = data.size();

        if(length <= 0) return;
        sum = data.get(0).getNum();
        if(length == 1){
            recordBean = new RecordBean(data.get(0).getmTime(),sum,mContext);
            data.add(0,recordBean);
        }else{
            while( j < length ){
                c1.setTimeInMillis(data.get(i).getmTime());
                c2.setTimeInMillis(data.get(j).getmTime());
                if(DateHelper.sameDay(c1,c2)){
                    sum += data.get(j).getNum();
                    j++;
                }else{
                    recordBean = new RecordBean(data.get(i).getmTime(),sum,mContext);
                    data.add(i,recordBean);
                    i = j + 1;
                    j = j + 2;
                    length ++;
                    sum = data.get(i).getNum();
                }
            }
            if(i < length){
                recordBean = new RecordBean(data.get(i).getmTime(),sum,mContext);
                data.add(i,recordBean);
            }
        }
    }

    private void onChangeData(){
        double sumIncome = 0, sumSpending = 0;
        RecordBean recordBean;
        mData = assistantDB.loadRecordAll(UserManager.getInstance(activity).getuID());
        for (int i = 0;i < mData.size(); i++){
            recordBean = mData.get(i);
            if(recordBean.getType() == TypeIconBean.TYPE_INCOME){
                sumIncome += recordBean.getmAmount();
            }else{
                sumSpending += recordBean.getmAmount();
            }
        }
        tvIncome.setText(String.format("%.2f",sumIncome));
        tvSpending.setText(String.format("%.2f",sumSpending));
        bindData(mData);
    }

}
