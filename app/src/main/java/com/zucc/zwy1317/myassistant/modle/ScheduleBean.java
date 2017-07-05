package com.zucc.zwy1317.myassistant.modle;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/3 15:24
 */

public class ScheduleBean {

    private String sID;
    private String mTitle;
    private String mNote;
    private Long mStartTime;
    private Long mEndTime;
    private Long mAlarmTime;
    private String mAlarmColor;
    private String mAlarmTonePath;
    private String uID;
    public ScheduleBean(){}
    public ScheduleBean(String sID, String title, String note, Long startTime, Long endTime, Long alarmTime, String alarmColor, String alarmTonePath, String uID){
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


}
