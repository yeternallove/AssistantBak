package com.zucc.zwy1317.myassistant.ui.customview.agenda;

import android.content.Context;
import android.util.AttributeSet;


import com.zucc.zwy1317.myassistant.modle.CalendarEventBean;
import com.zucc.zwy1317.myassistant.util.CalendarManager;
import com.zucc.zwy1317.myassistant.util.DateHelper;

import java.util.Calendar;
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

    /**
     * 根据传入的日期使 ListView 滚到对应的日期
     *
     * @param today 传入的日期
     */
    public void scrollToCurrentDate(Calendar today) {
        List<CalendarEventBean> events = CalendarManager.getInstance().getEvents();

        int toIndex = 0;
        for (int i = 0; i < events.size(); i++) {
            if (DateHelper.sameDate(today, events.get(i).getInstanceDay())) {
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

    // endregion
}
