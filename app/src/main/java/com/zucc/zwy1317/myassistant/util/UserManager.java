package com.zucc.zwy1317.myassistant.util;

import android.content.Context;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.modle.UserBean;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/13 12:27
 */

public class UserManager {
    public static final int THEME_DAY = 0;
    public static final int THEME_NIGHT = 1;
    private static UserManager mInstance;//单例模型
    private static int themeNow = THEME_DAY;
    private UserBean user;

    private UserManager(){
        this.user = new UserBean();
    }
    public static UserManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new UserManager();
        }
        switch (themeNow){
            case THEME_DAY:
                context.setTheme(R.style.AppTheme);
                break;
            case THEME_NIGHT:
                context.setTheme(R.style.NightTheme);
                break;
        }
        return mInstance;
    }

    public String getuID(){
        return user.getuID();
    }

    public static int getThemeNow() {
        return themeNow;
    }

    public static void setThemeNow(int themeNow) {
        UserManager.themeNow = themeNow;
    }
}
