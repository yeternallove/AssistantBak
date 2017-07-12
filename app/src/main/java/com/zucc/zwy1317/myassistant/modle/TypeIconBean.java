package com.zucc.zwy1317.myassistant.modle;

import com.zucc.zwy1317.myassistant.R;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/11 23:11
 */

public class TypeIconBean {
    public static final int TYPE_NULL= 0;
    public static final int TYPE_INCOME = 1;
    public static final int TYPE_SPENDING = 2;
    private int iID;
    private int type;
    private int icon;
    private String title;
    private String color; //保存格式为#000000

    public TypeIconBean(){
        this.type = TYPE_SPENDING;
        this.icon = R.drawable.liu_shui_anjiehuankuan;
    }

    public int getiID() {
        return iID;
    }

    public void setiID(int iID) {
        this.iID = iID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
