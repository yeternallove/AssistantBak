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
    private int mDayOfMonth; //相当于Day_OF_Month,一月中的第几天
    private int mDayOfWeek; //一个星期中的第几天
    private boolean mToday; //是否是今天
    private boolean mFirstDayOfMonth; //是否是当前月的第一天
    private boolean mSelected;//是否选中


    public DayItem(Date date,boolean today,boolean selected) {
        this.mDate = date;
        this.mToday = today;
        this.mSelected = selected;
    }

    public DayItem(Calendar calendar){
        this.mDate = calendar.getTime();
        this.mDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        this.mDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        this.mToday = false;
        this.mSelected = false;
        this.mFirstDayOfMonth =(mDayOfMonth == 1);
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public int getmDayOfMonth() {
        return mDayOfMonth;
    }

    public void setmDayOfMonth(int mDayOfMonth) {
        this.mDayOfMonth = mDayOfMonth;
    }

    public int getmDayOfWeek() {
        return mDayOfWeek;
    }

    public void setmDayOfWeek(int mDayOfWeek) {
        this.mDayOfWeek = mDayOfWeek;
    }

    public boolean ismToday() {
        return mToday;
    }

    public void setmToday(boolean mToday) {
        this.mToday = mToday;
    }

    public boolean ismFirstDayOfMonth() {
        return mFirstDayOfMonth;
    }

    public void setmFirstDayOfMonth(boolean mFirstDayOfMonth) {
        this.mFirstDayOfMonth = mFirstDayOfMonth;
    }

    public boolean ismSelected() {
        return mSelected;
    }

    public void setmSelected(boolean mSelected) {
        this.mSelected = mSelected;
    }

}
