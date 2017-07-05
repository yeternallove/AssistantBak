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
    private String mButtons;
    private String mMessage;
    public ChatBean(){}

    public ChatBean(String cID,String senderID,String recipientID,Long timestamp,String buttons,String message ){
        this.cID = cID;
        this.mSenderID = senderID;
        this.mRecipientID = recipientID;
        this.mTimestamp = timestamp;
        this.mButtons = buttons;
        this.mMessage = message;
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

    public String getmButtons() {
        return mButtons;
    }

    public void setmButtons(String mButtons) {
        this.mButtons = mButtons;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
