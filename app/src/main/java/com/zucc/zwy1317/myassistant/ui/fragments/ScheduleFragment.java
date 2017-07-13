package com.zucc.zwy1317.myassistant.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.db.AssistantDB;
import com.zucc.zwy1317.myassistant.modle.ScheduleBean;
import com.zucc.zwy1317.myassistant.ui.activities.AccountAddActivity;
import com.zucc.zwy1317.myassistant.ui.activities.MainActivity;
import com.zucc.zwy1317.myassistant.ui.activities.ScheduleAddActivity;
import com.zucc.zwy1317.myassistant.ui.base.BaseFragment;
import com.zucc.zwy1317.myassistant.ui.customview.agenda.AgendaAdapter;
import com.zucc.zwy1317.myassistant.ui.customview.agenda.AgendaView;
import com.zucc.zwy1317.myassistant.ui.customview.calendar.CalendarView;
import com.zucc.zwy1317.myassistant.util.BusProvider;
import com.zucc.zwy1317.myassistant.util.CalendarManager;
import com.zucc.zwy1317.myassistant.util.Events;
import com.zucc.zwy1317.myassistant.util.UserManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * 用于显示界面的 Pager ，通过设置不同的 adapter 实现布局复用
 */
public class ScheduleFragment extends BaseFragment implements StickyListHeadersListView.OnStickyHeaderChangedListener ,View.OnClickListener{
    public static final int REQ_NEW = 1;
    public static final int REQ_LOOK = 2;

    private MainActivity activity;
    public AgendaAdapter agendaAdapter;
    private int mCalendarHeaderColor, mCalendarDayTextColor, mCalendarPastDayTextColor, mCalendarCurrentDayColor;
    private AssistantDB assistantDB;
    private List<ScheduleBean> scheduleList;

    @BindView(R.id.schedule_calendar)
    CalendarView calendar_view;
    @BindView(R.id.schedule_agenda)
    AgendaView agenda_view;
    @BindView(R.id.schedule_fab)
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater , @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
//        View view = inflater.inflate(R.layout.fragment_account,container,false);
        View view = View.inflate(getContext(),R.layout.fragment_schedule,null);
        ButterKnife.bind(this,view);
        activity = (MainActivity)getActivity();
        activity.onFragmentHiddenChanged(false,MainActivity.TAG_SCH);
        initData();
        activity.getImgToday().setOnClickListener(this);
        fab.setOnClickListener(this);
        return view;
    }


    /**
     * 初始化数据
     */
    public void initData() {
        assistantDB = AssistantDB.getInstance(activity);
        scheduleList = assistantDB.loadScheduleAll(UserManager.getInstance(activity).getuID());
        mCalendarHeaderColor = R.color.calendar_header_day_background;
        mCalendarDayTextColor = R.color.calendar_text_day;
        mCalendarCurrentDayColor = R.color.calendar_text_current_day;
        mCalendarPastDayTextColor = R.color.calendar_text_past_day;
        calendar_view.findViewById(R.id.cal_day_names).setBackgroundColor(getResources().getColor(mCalendarHeaderColor));
        calendar_view.init(CalendarManager.getInstance(getActivity()), getResources().getColor(mCalendarDayTextColor), getResources().getColor(mCalendarCurrentDayColor), getResources().getColor(mCalendarPastDayTextColor));

        // 初始化AgendaView的数据适配器
        agendaAdapter = new AgendaAdapter(getActivity());
        agenda_view.getAgendaListView().setAdapter(agendaAdapter);

        //注册AgendaListView的OnStickyHeaderChanged监听事件
        agenda_view.getAgendaListView().setOnStickyHeaderChangedListener(this);
        agenda_view.getAgendaListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "鬼知道发生了什么", Toast.LENGTH_SHORT).show();

            }
        });
        //将日期与event进行匹配
        CalendarManager.getInstance().loadData(scheduleList);
    }

    /**
     * 当ListView的头部改变时,同时改变CalendarView的日期
     *
     * @param l            ListView
     * @param header       头部 header View
     * @param itemPosition 位置
     * @param headerId     头部 header ID
     */
    @Override
    public void onStickyHeaderChanged(StickyListHeadersListView l, View header, int itemPosition, long headerId) {

        if (CalendarManager.getInstance().getSchedules().size() > 0) {
            ScheduleBean event = CalendarManager.getInstance().getSchedules().get(itemPosition);
            if (event != null) {
                calendar_view.scrollToDate(CalendarManager.getInstance().getDays().get((int)event.getHeadID()));
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.schedule_fab:
                startActivityForResult(new Intent(activity, ScheduleAddActivity.class), REQ_NEW);
                break;
            case R.id.main_appbar_img_today:
                BusProvider.getInstance().send(new Events.GoBackToDay());
                calendar_view.scrollToDate(CalendarManager.getInstance().getToday(), CalendarManager.getInstance().getWeeks());
                break;
            default:
                break;
        }
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        activity.onFragmentHiddenChanged(hidden,MainActivity.TAG_SCH);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_NEW:
                if(resultCode == ScheduleAddActivity.SCH_ADD_SAVE){
                    scheduleList = assistantDB.loadScheduleAll(UserManager.getInstance(activity).getuID());
                    CalendarManager.getInstance().loadData(scheduleList);
                }
                break;
            case REQ_LOOK:
                break;
            default:
                break;
        }
    }
}
