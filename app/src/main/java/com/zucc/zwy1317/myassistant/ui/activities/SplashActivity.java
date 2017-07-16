package com.zucc.zwy1317.myassistant.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.ui.base.BaseActivity;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/5 13:09
 */

public class SplashActivity extends BaseActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SplashActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.actionStart(this);
    }
}
