package com.zucc.zwy1317.myassistant.ui.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.db.AssistantDB;
import com.zucc.zwy1317.myassistant.modle.ChatBean;
import com.zucc.zwy1317.myassistant.ui.activities.MainActivity;
import com.zucc.zwy1317.myassistant.ui.adapters.ChatAdapter;
import com.zucc.zwy1317.myassistant.ui.base.BaseFragment;
import com.zucc.zwy1317.myassistant.util.HttpHandler;
import com.zucc.zwy1317.myassistant.util.UserManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/12 09:05
 */

public class ChatFragment extends BaseFragment implements View.OnClickListener{

    private MainActivity activity;
    private AssistantDB db;
    private InputMethodManager imm;

    private ChatAdapter adapter;
    private ChatBean chatBean;
    private List<ChatBean> mPastList;
    private List<ChatBean> mNewList;
    private List<ChatBean> mList;

    private int index;
    private int hindex;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.chat_edt_send)
    EditText mEditText;
    @BindView(R.id.chat_btn_send)
    Button mBtnSend;


    public View onCreateView(LayoutInflater inflater , @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_chat,container,false);
//        View view = View.inflate(getContext(),R.layout.fragment_account,null);
        ButterKnife.bind(this,view);
        activity = (MainActivity)getActivity();
        activity.onFragmentHiddenChanged(false,MainActivity.TAG_CHAT);
        imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        db = AssistantDB.getInstance(getActivity());

        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetChat().execute((Void) null);
            }
        });

        mList = db.loadChatAll(UserManager.getInstance(activity).getuID());

        index = 0;
        hindex = Math.min(3,mList.size());//限制显示
        mPastList = new ArrayList<>();
        for(; index < hindex; index++){
            mPastList.add(mList.get(index));
        }
        mNewList = new ArrayList<>();
        adapter = new ChatAdapter(activity, mPastList, mNewList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true));
        mRecyclerView.setHasFixedSize(true);
        new GetChat().execute((Void) null);

        mBtnSend.setOnClickListener(this);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        activity.onFragmentHiddenChanged(hidden,MainActivity.TAG_CHAT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chat_btn_send:
                String msg = mEditText.getText().toString();
                if (msg == null || "".equals(msg)) {
                    Toast.makeText(activity, activity.getResources().getString(R.string.send_the_content_is_empty), Toast.LENGTH_SHORT).show();
                    break;
                }
                chatBean = new ChatBean(UserManager.getInstance(activity).getuID(),"eternallove", System.currentTimeMillis(), msg);
                chat(chatBean);
                //TODO:回复
                new SendChat().execute(chatBean.getmMessage(),UserManager.getInstance(activity).getuID());
                mEditText.setText("");
                break;
            default:
                break;
        }
    }

    public void chat(ChatBean chatBean){
        chatBean.bindID();
        mNewList.add(chatBean);
        adapter.notifyDataSetChanged();
        db.saveChat(chatBean);
    }


    private class GetChat extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //TODO : get date from ...then update mChatList
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            hindex = Math.min(hindex+5,mList.size());
            for(; index < hindex; index++){
                mPastList.add(mList.get(index));
            }
            adapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
        }

    }

    private class SendChat extends AsyncTask<String,Void,ChatBean> {

        @Override
        protected ChatBean doInBackground(String... send_message) {
            ChatBean chat = HttpHandler.sendMessage(send_message[0],send_message[1]);
            return chat;
        }

        @Override
        protected void onPostExecute(ChatBean chatMessage) {
            chat(chatMessage);
        }
    }
}
