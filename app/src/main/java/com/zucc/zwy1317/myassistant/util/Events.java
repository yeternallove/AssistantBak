package com.zucc.zwy1317.myassistant.util;

import com.zucc.zwy1317.myassistant.modle.DayItem;

import java.util.Calendar;
import java.util.Date;

/**
 * Events emitted by the bus provider.
 * EventBus可能出现的event事件类型
 */
public class Events {

    /**
     * Day的点击事件
     */
    public static class DayClickedEvent {

        private DayItem mDayItem;
        private Calendar mDate;

        public DayClickedEvent(DayItem dayItem) {
            this.mDayItem = dayItem;
            this.mDate = dayItem.getDate();
        }

        public DayItem getDay() {
            return mDayItem;
        }
        public Calendar getDate(){
            return mDate;
        }
    }

    /**
     * 回到今天时间
     */
    public static class GoBackToDay {
    }

    /**
     * Calendar的滑动事件
     */
    public static class CalendarScrolledEvent {
    }

    /**
     * 日程View的触摸事件
     */
    public static class AgendaListViewTouchedEvent {
    }


    /**
     * 暂时理解为 事件与日期匹配
     */
    public static class EventsFetched {
    }


    public static class ForecastFetched {
    }

    public static class CalendarMonthEvent {

        public String month;

        public CalendarMonthEvent(String month) {
            this.month = month;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }
    }
}
