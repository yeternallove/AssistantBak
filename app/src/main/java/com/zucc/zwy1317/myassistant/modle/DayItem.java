package com.zucc.zwy1317.myassistant.modle;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/9 13:45
 */
public class DayItem implements Serializable {

    private Date mDate; //日期
    private int mValue; //相当于Day_OF_Month,一月中的第几天
    private int mDayOfWeek; //一个星期中的第几天
    private boolean mToday; //是否是今天
    private boolean mFirstDayOfMonth; //是否是当前月的第一天
    private boolean mSelected;//是否选中
    private String mMonth;//每个月第一天显示的


    public DayItem(Date date,boolean today,boolean selected) {
        this.mDate = date;
        this.mToday = today;
        this.mSelected = selected;
    }

    public DayItem(Calendar calendar){
        this.mDate = calendar.getTime();
        this.mValue = calendar.get(Calendar.DAY_OF_MONTH);
        this.mDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        this.mToday = false;
        this.mSelected = false;
        this.mFirstDayOfMonth =(mValue == 1);
        this.mMonth = null;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int mDayOfMonth) {
        this.mValue = mDayOfMonth;
    }

    public int getDayOfWeek() {
        return mDayOfWeek;
    }

    public void setDayOfWeek(int mDayOfWeek) {
        this.mDayOfWeek = mDayOfWeek;
    }

    public boolean isToday() {
        return mToday;
    }

    public void setToday(boolean mToday) {
        this.mToday = mToday;
    }

    public boolean isFirstDayOfMonth() {
        return mFirstDayOfMonth;
    }

    public void setFirstDayOfMonth(boolean mFirstDayOfMonth) {
        this.mFirstDayOfMonth = mFirstDayOfMonth;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean mSelected) {
        this.mSelected = mSelected;
    }

    public String getMonth() {
        return mMonth;
    }

    public void setMonth(String mMonth) {
        this.mMonth = mMonth;
    }
}
