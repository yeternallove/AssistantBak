package com.zucc.zwy1317.myassistant.modle;


import android.content.Context;

import com.zucc.zwy1317.myassistant.util.DateUtil;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/3 15:24
 */

public class RecordBean {

    private String rID;
    private double mAmount;
    private long mTime;
    private int mType;
    private String mTitle; //标签比如花钱·餐饮
    private String mNote;
    private String location;
    private String photo;
    private String uID;

    public RecordBean(){}

    public RecordBean(String uID){
        this.uID = uID;
        this.rID = null;
        this.mType = TypeIconBean.TYPE_NULL;
    }
    public RecordBean(long time, double amount, Context context){
        this.mType = TypeIconBean.TYPE_NULL;
        this.mTime = time;
        this.mAmount = amount;
        this.mTitle = DateUtil.getDateString(context,this.mTime);
    }
    public RecordBean( String rID, double amount,long time,int type,String title, String note,String location,String photo,String uID){
        this.rID = rID;
        this.mAmount = amount;
        this.mTime = time;
        this.mType = type;
        this.mTitle = title;
        this.mNote = note;
        this.location = location;
        this.photo = photo;
        this.uID = uID;
    }

    public RecordBean bindID(){
        this.rID = String.format("%s%d",this.getuID(),this.getmTime());
        return this;
    }

    public double getNum(){
        if(this.getType() == TypeIconBean.TYPE_INCOME)
            return this.getmAmount();
        else return this.getmAmount()*-1;
    }

    public String getrID() {
        return rID;
    }

    public void setrID(String rID) {
        this.rID = rID;
    }

    public double getmAmount() {
        return mAmount;
    }

    public void setmAmount(double mAmount) {
        this.mAmount = mAmount;
    }

    public long getmTime() {
        return mTime;
    }

    public void setmTime(long mTime) {
        this.mTime = mTime;
    }

    public int getType() {
        return mType;
    }

    public void setType(int mType) {
        this.mType = mType;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
