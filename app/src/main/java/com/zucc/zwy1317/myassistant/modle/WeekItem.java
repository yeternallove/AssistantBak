package com.zucc.zwy1317.myassistant.modle;

import java.util.Date;
import java.util.List;

/**
 * Week model class.
 * Week 周的JavaBean
 */
public class WeekItem {
    private boolean mMiddleOfMonth;//月中显示月份标签（默认第15天）
    private String mLabel;
    private int mWeekInYear;
    private int mYear;
    private List<DayItem> mDayItems;

    public WeekItem(boolean mMiddleOfMonth, String label,int year,int weekInYear) {
        this.mMiddleOfMonth = mMiddleOfMonth;
        this.mLabel = label;
        this.mYear = year;
        this.mWeekInYear = weekInYear;
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
