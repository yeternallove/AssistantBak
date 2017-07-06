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
import com.zucc.zwy1317.myassistant.util.DateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private List<ItemRecord> mList = new ArrayList<>();

    public AccountAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<RecordBean> data) {
        mData = data;

        notifyDataSetChanged();
    }

    @Override
    public AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AccountViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_account_income, parent, false));
    }

    @Override
    public void onBindViewHolder(AccountViewHolder holder, int position) {
        RecordBean recordBean = mData.get(position);
        if(recordBean.getMisIncome() == null){
            holder.mTvIncome.setText(DateUtil.getDateString(mContext,recordBean.getmTime()));
        }
        else if(recordBean.getMisIncome()){
            holder.mTvIncome.setText(recordBean.getmTitle()+recordBean.getmAmount()+"");
        }
        else {
            holder.mTvSpending.setText(recordBean.getmTitle()+recordBean.getmAmount()+"");
        }

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    private class ItemRecord extends RecordBean{
        private boolean isHead;

        public boolean isHead() {
            return isHead;
        }

        public void setHead(boolean head) {
            isHead = head;
        }
    }
    static class AccountViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_account_tv_income)
        TextView mTvIncome;
        @BindView(R.id.item_account_tv_spending)
        TextView mTvSpending;
        @BindView(R.id.item_account_iv)
        ImageView mImageView;
        public AccountViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}