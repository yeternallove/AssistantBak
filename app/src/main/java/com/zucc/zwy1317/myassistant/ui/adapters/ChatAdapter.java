package com.zucc.zwy1317.myassistant.ui.adapters;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.db.AssistantDB;
import com.zucc.zwy1317.myassistant.modle.ChatBean;
import com.zucc.zwy1317.myassistant.util.CalendarManager;
import com.zucc.zwy1317.myassistant.util.UserManager;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @description:
 * @author: eternallove
 * @date: 2016/12/25
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {

    private static final int TYPE_BOTTOM = 0x0;
    private static final int TYPE_MESSAGE_SEND = 0x1;
    private static final int TYPE_MESSAGE_RECEIVED = 0x3;
    private final String mID;
    private List<ChatBean> mPastList;
    private List<ChatBean> mNewList;

    private Context mContext;
    private LayoutInflater layoutInflater;
    private SimpleDateFormat mFormat;
    private ClipboardManager cmb;
    private AssistantDB db;

    public ChatAdapter(Context context, List<ChatBean> pastList, List<ChatBean> newList) {
        this.mPastList = pastList;
        this.mNewList = newList;
        this.mContext = context;
        this.mID = UserManager.getInstance(mContext).getuID();
        this.layoutInflater = LayoutInflater.from(mContext);
        this.cmb = (ClipboardManager)mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        this.db = AssistantDB.getInstance(mContext);
        this.mFormat = CalendarManager.getInstance().getMessageTimeFormat();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0 ){
            return TYPE_BOTTOM;
        }
        else {
            ChatBean chatBean = getRecivedBean(position);
            if(chatBean.getmSenderID().equals(mID)){
                return TYPE_MESSAGE_SEND;
            }else {
                return TYPE_MESSAGE_RECEIVED;
            }
        }
    }

    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_MESSAGE_SEND:
                return new MessageHolder(layoutInflater.inflate(R.layout.item_sent_message, parent, false));
            case TYPE_MESSAGE_RECEIVED:
                return new MessageHolder(layoutInflater.inflate(R.layout.item_chat_message, parent, false));
            case TYPE_BOTTOM:
                return new ChatHolder(layoutInflater.inflate(R.layout.item_chat_null,parent, false));
            default:return null;
        }
    }

    @Override
    public void onBindViewHolder(ChatHolder holder, int position) {

        switch (getItemViewType(position)){
            case TYPE_MESSAGE_SEND:
                onBindViewMessageHolder(holder,position);
                break;
            case TYPE_MESSAGE_RECEIVED:
                onBindViewMessageHolder(holder,position);
                break;
            default:break;
        }
    }

    private void onBindViewMessageHolder(ChatHolder holder, final int position){
        final ChatBean chatBean = getRecivedBean(position);
        MessageHolder messageHolder = (MessageHolder) holder;
        messageHolder.ChatContent.setText(chatBean.getmMessage());

//        messageHolder.imgUserhead
        if(position < getItemCount()-1){
            ChatBean chatMessageOld = getRecivedBean(position+1);
            if(chatBean.getmTimestamp()-chatMessageOld.getmTimestamp() > 1800000){
                messageHolder.Timestamp.setText(mFormat.format(chatBean.getmTimestamp()));
            }else{
                messageHolder.Timestamp.setVisibility(View.GONE);
            }
        }else {
            messageHolder.Timestamp.setText(mFormat.format(chatBean.getmTimestamp()));
        }
        messageHolder.UserName.setVisibility(View.GONE);
    }



    private ChatBean getRecivedBean(int position){
        int length = mNewList.size();
        if(position > length ){
            return mPastList.get(position - length - 1);
        }else{
            return mNewList.get(length  - position);
        }
    }

    private boolean removeReciveditem(int position){
        int length = mNewList.size();
        if(position > length ){
            mPastList.remove(position - length - 1);
        }else{
           mNewList.remove(length  - position);
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return mNewList.size()+ mPastList.size() + 1;
    }

    static class ChatHolder extends RecyclerView.ViewHolder {

        ChatHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    static class MessageHolder extends ChatHolder {
        @BindView(R.id.tv_chatcontent)  TextView ChatContent;
        @BindView(R.id.timestamp)       TextView Timestamp;
        @BindView(R.id.tv_userName)     TextView UserName;
        @BindView(R.id.img_userhead)    ImageView Userhead;
        MessageHolder(View itemView) {
            super(itemView);
        }
    }

}
