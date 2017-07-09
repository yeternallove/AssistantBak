package com.zucc.zwy1317.myassistant.modle;

import java.util.Date;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/3 15:24
 */

public class ScheduleBean {

    private String sID;
    private String mTitle;
    private String mNote;
    private Date date;
    private long mStartTime;
    private long mEndTime;
    private long mAlarmTime;
    private String mAlarmColor;
    private String mAlarmTonePath;
    private String uID;
    public ScheduleBean(){}
    public ScheduleBean(String sID, String title, String note, long startTime, long endTime, long alarmTime, String alarmColor, String alarmTonePath, String uID){
        this.sID = sID;
        this.mTitle = title;
        this.mNote = note;;
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        this.mAlarmTime = alarmTime;
        this.mAlarmColor = alarmColor;
        this.mAlarmTonePath = alarmTonePath;
        this.uID = uID;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getmStartTime() {
        return mStartTime;
    }

    public void setmStartTime(long mStartTime) {
        this.mStartTime = mStartTime;
    }

    public long getmEndTime() {
        return mEndTime;
    }

    public void setmEndTime(long mEndTime) {
        this.mEndTime = mEndTime;
    }

    public long getmAlarmTime() {
        return mAlarmTime;
    }

    public void setmAlarmTime(long mAlarmTime) {
        this.mAlarmTime = mAlarmTime;
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
}
