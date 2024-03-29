package com.zucc.zwy1317.myassistant.util;

import android.content.Context;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.modle.WeekItem;

import java.util.Calendar;
import java.util.Date;

/**
 * @description:
 * @author: eternallove
 * @date: 2016/11/7
 */
public class DateHelper {
    private static final long MINUTE = 1000 * 60;
    private static final long HOUR = MINUTE * 60;
    private static final long DAY = HOUR * 24;
    private static final long WEEK = DAY * 7;
    private static final long MONTH = DAY * 30;
    private static final long YEAR = DAY * 365;
    private static final String COUNT_REPLACEMENT = "{count}";

    public static String getDateString(Context context, Date date) {
        if (date == null) {
            date = new Date();
        }
        return getDateString(context,date.getTime());
    }

    public static String getDateString(Context context,long last){
        long now = System.currentTimeMillis();
        long offset = now - last;
        if (offset <= DAY) {
//            if(offset <= MINUTE){
//                return context.getString(R.string.just_now);
//            }
//            else if(offset <= HOUR){
//                return context.getString(R.string.minutes_ago)
//                        .replace(COUNT_REPLACEMENT, String.valueOf(offset / HOUR));
//            } else{
//                return context.getString(R.string.hours_ago)
//                        .replace(COUNT_REPLACEMENT, String.valueOf(offset / HOUR));
//            }
            return context.getResources().getString(R.string.today);
        } else if (offset <= DAY * 2) {
            return context.getString(R.string.yesterday);
        } else if (offset <= WEEK) {
            return context.getString(R.string.days_ago)
                    .replace(COUNT_REPLACEMENT, String.valueOf(offset / DAY));
        }  else if (offset <= MONTH) {
            return context.getString(R.string.months_ago)
                    .replace(COUNT_REPLACEMENT, String.valueOf(offset / WEEK));
        } else if (offset <= YEAR) {
            return context.getString(R.string.months_ago)
                    .replace(COUNT_REPLACEMENT, String.valueOf(offset / MONTH));
        } else {
            return context.getString(R.string.years_ago)
                    .replace(COUNT_REPLACEMENT, String.valueOf(offset / YEAR));
        }
    }

    public static boolean sameDay(Calendar c1, Calendar c2){
        return  c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                    && c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                    && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean sameWeek(Calendar cal, WeekItem week) {
        return ((int)cal.get(Calendar.WEEK_OF_YEAR) == week.getWeekInYear() && (int)cal.get(Calendar.YEAR) == week.getYear());
    }
}