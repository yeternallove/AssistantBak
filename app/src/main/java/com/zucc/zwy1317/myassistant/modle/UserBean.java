package com.zucc.zwy1317.myassistant.modle;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/3 15:23
 */

public class UserBean {

    private String uID;
    private String mQQ;
    private String mEmal;
    private String mNickname;
    private String mPwd;
    private String mAvatar;
    private String mData;

    public UserBean(){}
    public UserBean( String uID, String QQ, String emal, String nickname, String pwd, String avatar, String data){
        this.uID = uID;
        this.mQQ = QQ;
        this.mEmal = emal;
        this.mNickname = nickname;
        this.mPwd = pwd;
        this.mAvatar = avatar;
        this.mData = data;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getmQQ() {
        return mQQ;
    }

    public void setmQQ(String mQQ) {
        this.mQQ = mQQ;
    }

    public String getmEmal() {
        return mEmal;
    }

    public void setmEmal(String mEmal) {
        this.mEmal = mEmal;
    }

    public String getmNickname() {
        return mNickname;
    }

    public void setmNickname(String mNickname) {
        this.mNickname = mNickname;
    }

    public String getmPwd() {
        return mPwd;
    }

    public void setmPwd(String mPwd) {
        this.mPwd = mPwd;
    }

    public String getmAvatar() {
        return mAvatar;
    }

    public void setmAvatar(String mAvatar) {
        this.mAvatar = mAvatar;
    }

    public String getmData() {
        return mData;
    }

    public void setmData(String mData) {
        this.mData = mData;
    }
}
