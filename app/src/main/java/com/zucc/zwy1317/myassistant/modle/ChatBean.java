package com.zucc.zwy1317.myassistant.modle;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/3 15:23
 */

public class ChatBean {

    private String cID;
    private String mSenderID;
    private String mRecipientID;
    private Long mTimestamp;
    private String mMessage;
    public ChatBean(){}

    public ChatBean(String senderID,String recipientID,Long timestamp,String message ){
        this.cID = null;
        this.mSenderID = senderID;
        this.mRecipientID = recipientID;
        this.mTimestamp = timestamp;
        this.mMessage = message;
    }

    public ChatBean bindID(){
        if(cID == null){
            this.cID = String.format("%s%s%d", this.getmSenderID(), this.getmRecipientID(), this.getmTimestamp());
        }
        return this;
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getmSenderID() {
        return mSenderID;
    }

    public void setmSenderID(String mSenderID) {
        this.mSenderID = mSenderID;
    }

    public String getmRecipientID() {
        return mRecipientID;
    }

    public void setmRecipientID(String mRecipientID) {
        this.mRecipientID = mRecipientID;
    }

    public Long getmTimestamp() {
        return mTimestamp;
    }

    public void setmTimestamp(Long mTimestamp) {
        this.mTimestamp = mTimestamp;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
