package com.zucc.zwy1317.myassistant.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.modle.RecordBean;
import com.zucc.zwy1317.myassistant.modle.TypeIconBean;
import com.zucc.zwy1317.myassistant.util.DateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/6 14:39
 */

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {
    private Context mContext;
    private List<RecordBean> mData = new ArrayList<>();
    private HashMap<String,TypeIconBean> map = new HashMap<>();

    public AccountAdapter(Context context , List<RecordBean> data, HashMap<String,TypeIconBean> map) {
        this.mContext = context;
        this.mData = data;
        this.map = map;
    }

    public void updateData(List<RecordBean> data) {
        mData = data;
        notifyDataSetChanged();
    }
    public void updateTypeIcon(HashMap<String,TypeIconBean> map){
        this.map = map;
    }


    @Override
    public AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AccountViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_account, parent, false));
    }

    @Override
    public void onBindViewHolder(AccountViewHolder holder, int position) {
        RecordBean recordBean = mData.get(position);
        if(position + 1 == getItemCount()){
            holder.mView.setVisibility(View.GONE);
        }else{
            holder.mView.setVisibility(View.VISIBLE);
        }
        holder.mTvIncome.setVisibility(View.VISIBLE);
        holder.mTvSpending.setVisibility(View.VISIBLE);
        if(recordBean.getType() == TypeIconBean.TYPE_NULL){
            holder.mTvIncome.setText(recordBean.getTitle());
            holder.mTvSpending.setText(String.format("%.2f",recordBean.getmAmount()));
        }
        else if(recordBean.getType() == TypeIconBean.TYPE_INCOME){
            holder.mTvIncome.setText(String.format("%s%.2f",recordBean.getTitle(),recordBean.getmAmount()));
            holder.mTvSpending.setVisibility(View.GONE);
            holder.mImageView.setImageResource(map.get(recordBean.getTitle()).getIcon());
        }
        else if(recordBean.getType() == TypeIconBean.TYPE_SPENDING){
            holder.mTvIncome.setVisibility(View.GONE);
            holder.mTvSpending.setText(String.format("%s%.2f",recordBean.getTitle(),recordBean.getmAmount()));
            holder.mImageView.setImageResource(map.get(recordBean.getTitle()).getIcon());
        }

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class AccountViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_account_tv_income)
        TextView mTvIncome;
        @BindView(R.id.item_account_tv_spending)
        TextView mTvSpending;
        @BindView(R.id.item_account_iv)
        ImageView mImageView;
        @BindView(R.id.item_account_view_bottom)
        View mView;
        public AccountViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}