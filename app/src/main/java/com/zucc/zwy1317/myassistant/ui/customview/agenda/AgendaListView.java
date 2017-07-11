package com.zucc.zwy1317.myassistant.ui.customview.agenda;

import android.content.Context;
import android.util.AttributeSet;


import com.zucc.zwy1317.myassistant.modle.DayItem;
import com.zucc.zwy1317.myassistant.util.CalendarManager;
import com.zucc.zwy1317.myassistant.util.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * StickyListHeadersListView 是一个可以实现滑动顶部停靠的ListView
 */
public class AgendaListView extends StickyListHeadersListView {


    public AgendaListView(Context context) {
        super(context);
    }

    public AgendaListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AgendaListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void scrollToCurrentDate(Calendar today) {
        List<DayItem> days = CalendarManager.getInstance().getDays();

        int toIndex = 0;
        for (int i = 0; i < days.size(); i++) {
            if (DateUtil.sameCalendar(today,days.get(i).getDate())) {
                toIndex = i;
                break;
            }
        }
        final int finalToIndex = toIndex;
        post(new Runnable() {
            @Override
            public void run() {
                setSelection(finalToIndex);
            }
        });

    }

}
