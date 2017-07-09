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
    private List<DayItem> mDayItems;

    public WeekItem(boolean mMiddleOfMonth, String label) {
        this.mMiddleOfMonth = mMiddleOfMonth;
        this.mLabel = label;
    }

    public boolean ismMiddleOfMonth() {
        return mMiddleOfMonth;
    }

    public void setmMiddleOfMonth(boolean mMiddleOfMonth) {
        this.mMiddleOfMonth = mMiddleOfMonth;
    }

    public String getmLabel() {
        return mLabel;
    }

    public void setmLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    public List<DayItem> getmDayItems() {
        return mDayItems;
    }

    public void setmDayItems(List<DayItem> mDayItems) {
        this.mDayItems = mDayItems;
    }
}
