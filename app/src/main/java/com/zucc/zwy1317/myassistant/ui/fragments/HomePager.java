package com.zucc.zwy1317.myassistant.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.modle.ScheduleBean;
import com.zucc.zwy1317.myassistant.ui.base.BaseFragment;
import com.zucc.zwy1317.myassistant.ui.customview.agenda.AgendaAdapter;
import com.zucc.zwy1317.myassistant.ui.customview.agenda.AgendaView;
import com.zucc.zwy1317.myassistant.ui.customview.calendar.CalendarView;
import com.zucc.zwy1317.myassistant.util.CalendarManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * 用于显示界面的 Pager ，通过设置不同的 adapter 实现布局复用
 */
public class HomePager extends BaseFragment implements StickyListHeadersListView.OnStickyHeaderChangedListener {
    @BindView(R.id.test_calendar)
    CalendarView calendar_view;
    @BindView(R.id.test_agenda)
    AgendaView agenda_view;
    public AgendaAdapter agendaAdapter;
    private int mCalendarHeaderColor, mCalendarDayTextColor, mCalendarPastDayTextColor, mCalendarCurrentDayColor;


    @Override
    public View onCreateView(LayoutInflater inflater , @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
//        View view = inflater.inflate(R.layout.fragment_account,container,false);
        View view = View.inflate(getContext(),R.layout.fragment_schedule,null);
        ButterKnife.bind(this,view);
        initData();
        return view;
    }


    /**
     * 初始化数据
     */
    public void initData() {
        initCalendarInfo();
        //初始化数据
        List eventList = new ArrayList<>();
        List<ScheduleBean> scheduleList = new ArrayList<>();
        //TODO
//                = AssistantDB.getInstance(getActivity()).getAll();
//        mockList(eventList, alllist);
        mCalendarHeaderColor = R.color.calendar_header_day_background;
        mCalendarDayTextColor = R.color.calendar_text_day;
        mCalendarCurrentDayColor = R.color.calendar_text_current_day;
        mCalendarPastDayTextColor = R.color.calendar_text_past_day;
        calendar_view.findViewById(R.id.cal_day_names).setBackgroundColor(getResources().getColor(mCalendarHeaderColor));
        calendar_view.init(CalendarManager.getInstance(getActivity()), getResources().getColor(mCalendarDayTextColor), getResources().getColor(mCalendarCurrentDayColor), getResources().getColor(mCalendarPastDayTextColor));

        // 初始化AgendaView的数据适配器
        agendaAdapter = new AgendaAdapter(getActivity(),scheduleList);
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

    private void initCalendarInfo() {
        // 设置日历显示的时间，最大为当前时间+1年，最小为当前时间-2月
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -10);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);
        //根据你传入的开始结束值，构建生成Calendar数据（各种Item，JavaBean）
        CalendarManager.getInstance(getActivity()).buildCal(minDate, maxDate, Locale.getDefault());
    }

}
