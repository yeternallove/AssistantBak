package com.zucc.zwy1317.myassistant.util;

import android.content.Context;
import android.util.Log;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.modle.CalendarEventBean;
import com.zucc.zwy1317.myassistant.modle.DayItem;
import com.zucc.zwy1317.myassistant.modle.ScheduleBean;
import com.zucc.zwy1317.myassistant.modle.WeekItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * 用于一些日历信息的管理
 */
public class CalendarManager {

    private static final String LOG_TAG = CalendarManager.class.getSimpleName();

    private static CalendarManager mInstance;//单例模型

    private Context mContext;
    private Locale mLocale;
    private Calendar mToday = Calendar.getInstance();

    //SimpleDateFormat 是一个以国别敏感的方式格式化和分析数据的具体类。 它允许格式化 (date -> text)、语法分析 (text -> date)和标准化。
    private SimpleDateFormat mWeekdayFormatter;
    private SimpleDateFormat mMonthHalfNameFormat;

    private List<DayItem> mDays = new ArrayList<>();
    private List<WeekItem> mWeeks = new ArrayList<>();
    private HashMap<Long,List<ScheduleBean>> mData = new HashMap<>();

    private Calendar mWeekCounter;

    private CalendarManager(Context context) {
        this.mContext = context;
    }

    public static CalendarManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CalendarManager(context);
        }
        return mInstance;
    }

    public static CalendarManager getInstance() {
        return mInstance;
    }

    public void setLocale(Locale locale) {
        this.mLocale = locale;

        //apply the same locale to all variables depending on that
        //采用相同的区域语言环境
        setToday(Calendar.getInstance(mLocale));
        mWeekdayFormatter = new SimpleDateFormat(mContext.getString(R.string.day_name_format), mLocale);
        mMonthHalfNameFormat = new SimpleDateFormat(mContext.getString(R.string.month_half_name_format), mLocale);
    }

    public Locale getLocale() {
        return mLocale;
    }

    public Locale getmLocale() {
        return mLocale;
    }

    public void setmLocale(Locale mLocale) {
        this.mLocale = mLocale;
    }

    public Calendar getmToday() {
        return mToday;
    }

    public void setToday(Calendar mToday) {
        this.mToday = mToday;
    }

    public void buildCal(Calendar minDate, Calendar maxDate, Locale locale) {//时间段以月为最小单位
        //异常处理
        if (minDate == null || maxDate == null) {
            throw new IllegalArgumentException(
                    "minDate and maxDate must be non-null.");
        }
        if (minDate.after(maxDate)) {
            throw new IllegalArgumentException(
                    "minDate must be before maxDate.");
        }
        if (locale == null) {
            throw new IllegalArgumentException("Locale is null.");
        }
        //构建基本的Calendar
        setLocale(locale);

        mDays.clear();
        mWeeks.clear();
        mData.clear();

        Calendar mMaxCal = Calendar.getInstance(mLocale);
        mWeekCounter = Calendar.getInstance(mLocale);

        mMaxCal.setTime(maxDate.getTime());

        // -1,不包括最大的那天 比如我是1990 01 01到2000 01 01，我不要2000 01 01
        mMaxCal.add(Calendar.MINUTE, -1);

        mWeekCounter.setTime(minDate.getTime());

        int maxMonth = mMaxCal.get(Calendar.MONTH);//日历所存在的最大月
        int maxYear = mMaxCal.get(Calendar.YEAR);//日历所存在的最大年

        int tmpMonth = 0;//记录月

    }
}
