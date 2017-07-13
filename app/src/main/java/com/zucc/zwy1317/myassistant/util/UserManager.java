package com.zucc.zwy1317.myassistant.util;

import android.content.Context;

import com.zucc.zwy1317.myassistant.modle.UserBean;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/13 12:27
 */

public class UserManager {
    private static UserManager mInstance;//单例模型
    private UserBean user;

    private UserManager(){
        this.user = new UserBean();
    }
    public static UserManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new UserManager();
        }
        return mInstance;
    }

    public String getuID(){
        return user.getuID();
    }

}
