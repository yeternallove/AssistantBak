package com.zucc.zwy1317.myassistant.modle;


import com.zucc.zwy1317.myassistant.util.CalendarManager;
import com.zucc.zwy1317.myassistant.util.DateUtil;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/3 15:24
 */

public class ScheduleBean implements Serializable {

    private String sID;
    private String mTitle;
    private String mNote;
    private Calendar date;
    private long mStartTime;
    private long mEndTime;
    private long mAlarmTime;
    private String mAlarmColor;
    private String mAlarmTonePath;
    private String uID;
    private long HeadID;//分组头

    public ScheduleBean(){
        this.sID = null;
        this.mTitle = null;
        this.mNote = null;
        this.date = null;
        this.mStartTime = 0;
        this.mEndTime = 0;
        this.mAlarmTime = 0;
        this.mAlarmColor = null;
        this.mAlarmTonePath = null;
        this.uID = null;
        this.HeadID = -1;
    }
    public ScheduleBean(String uID){
        this();
        this.setuID(uID);
    }
    public ScheduleBean(long headID,Calendar calendar){
        this();
        setHeadID(headID);
        setDate(calendar);
    }
    public ScheduleBean(String sID, String title, String note, long startTime, long endTime, long alarmTime, String alarmColor, String alarmTonePath, String uID){
        this.sID = sID;
        this.mTitle = title;
        this.mNote = note;
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        this.mAlarmTime = alarmTime;
        this.mAlarmColor = alarmColor;
        this.mAlarmTonePath = alarmTonePath;
        this.uID = uID;
        setDate(startTime);
    }
    public ScheduleBean bindID(){
        if(getsID() == null) {
            this.sID = String.format("%s%d%s", this.getuID(), this.getmStartTime(),this.getmTitle());
        }
        return this;
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmNote() {
        return mNote;
    }

    public void setmNote(String mNote) {
        this.mNote = mNote;
    }

    public Calendar getDate() {
        if(this.date == null){
            this.date = Calendar.getInstance();
            this.date.setTimeInMillis(getmStartTime());
        }
        return this.date;
    }

    public void setDate(Calendar date) {
        this.date = Calendar.getInstance();
        this.date.setTime(date.getTime());
    }
    private void setDate(long time){
        this.date = Calendar.getInstance();
        this.date.setTimeInMillis(time);
    }

    public long getmStartTime() {
        return mStartTime;
    }

    public void setmStartTime(long mStartTime) {
        this.mStartTime = mStartTime;
    }

    public void setmStartTime(Calendar mStartTime) {
        this.mStartTime = mStartTime.getTimeInMillis();
    }

    public long getmEndTime() {
        return mEndTime;
    }

    public void setmEndTime(long mEndTime) {
        this.mEndTime = mEndTime;
    }

    public void setmEndTime(Calendar mEndTime) {
        this.mEndTime = mEndTime.getTimeInMillis();
    }

    public long getmAlarmTime() {
        return mAlarmTime;
    }

    public void setmAlarmTime(long mAlarmTime) {
        this.mAlarmTime = mAlarmTime;
    }

    public void setmAlarmTime(Calendar mAlarmTime) {
        this.mAlarmTime = mAlarmTime.getTimeInMillis();
    }

    public String getmAlarmColor() {
        return mAlarmColor;
    }

    public void setmAlarmColor(String mAlarmColor) {
        this.mAlarmColor = mAlarmColor;
    }

    public String getmAlarmTonePath() {
        return mAlarmTonePath;
    }

    public void setmAlarmTonePath(String mAlarmTonePath) {
        this.mAlarmTonePath = mAlarmTonePath;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public long getHeadID() {
        return HeadID;
    }

    public void setHeadID(long headID) {
        HeadID = headID;
    }

    @Override
    public String toString(){
        return getmTitle();
    }
}
