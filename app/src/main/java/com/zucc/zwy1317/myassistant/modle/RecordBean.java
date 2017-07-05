package com.zucc.zwy1317.myassistant.modle;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/3 15:24
 */

public class RecordBean {
    private String rID;
    private String mAmount;
    private String mTime;
    private Boolean misIncome;
    private String mTitle;
    private String mNote;
    private String uID;

    public RecordBean(){}

    public RecordBean( String rID, String amount,String time, Boolean isIncome,String title, String note,String uID){
        this.rID = rID;
        this.mAmount = amount;
        this.mTime = time;
        this.misIncome = isIncome;
        this.mTitle = title;
        this.mNote = note;
        this.uID = uID;
    }

    public String getrID() {
        return rID;
    }

    public void setrID(String rID) {
        this.rID = rID;
    }

    public String getmAmount() {
        return mAmount;
    }

    public void setmAmount(String mAmount) {
        this.mAmount = mAmount;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public Boolean getMisIncome() {
        return misIncome;
    }

    public void setMisIncome(Boolean misIncome) {
        this.misIncome = misIncome;
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

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }
}
