package com.zucc.zwy1317.myassistant.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.modle.TypeIconBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/6 14:39
 */

public class TypeIconAdapter extends RecyclerView.Adapter<TypeIconAdapter.TypeIconHolder> {
    private Context mContext;
    private List<TypeIconBean> mData;
    private MyItemClickListener mItemClickListener;

    public TypeIconAdapter(Context context,List<TypeIconBean> data,MyItemClickListener myItemClickListener) {
        mContext = context;
        this.mData = data;
        this.mItemClickListener = myItemClickListener;
    }
    @Override
    public TypeIconHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TypeIconHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_typeicon, parent, false),mItemClickListener);
    }

    @Override
    public void onBindViewHolder(TypeIconHolder holder, int position) {
        TypeIconBean typeIconBean = mData.get(position);
        holder.mImg.setImageResource(typeIconBean.getIcon());
        holder.mTv.setText(typeIconBean.getTitle());
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class TypeIconHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private MyItemClickListener listener;

        @BindView(R.id.item_typeicon_img)
        ImageView mImg;
        @BindView(R.id.item_typeicon_tv)
        TextView mTv;
        public TypeIconHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(listener != null){
                listener.onItemClick(view,getPosition());
            }
        }
    }
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }
}