package com.zucc.zwy1317.myassistant.modle;

import com.zucc.zwy1317.myassistant.ui.customview.calendar.CalendarView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Week model class.
 * Week 周的JavaBean
 */
public class WeekItem {
    private boolean mMiddleOfMonth;//月中显示月份标签（默认第15天）
    private String mLabel;
    private int mWeekInYear;//一周中的第一天
    private int mYear;//一周中的第一天
    private List<DayItem> mDayItems;

    public WeekItem(Calendar firstDayOfWeek) {
        this.mMiddleOfMonth = false;
        this.mLabel = null;
        this.mYear = firstDayOfWeek.get(Calendar.YEAR);
        this.mWeekInYear = firstDayOfWeek.get(Calendar.WEEK_OF_YEAR);
    }

    public boolean ismMiddleOfMonth() {
        return mMiddleOfMonth;
    }

    public void setMiddleOfMonth(boolean mMiddleOfMonth) {
        this.mMiddleOfMonth = mMiddleOfMonth;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setmLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    public List<DayItem> getDayItems() {
        return mDayItems;
    }

    public void setDayItems(List<DayItem> mDayItems) {
        this.mDayItems = mDayItems;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int mYear) {
        this.mYear = mYear;
    }

    public int getWeekInYear() {
        return mWeekInYear;
    }

    public void setWeekInYear(int mWeekInYear) {
        this.mWeekInYear = mWeekInYear;
    }
}
