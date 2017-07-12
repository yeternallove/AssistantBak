package com.zucc.zwy1317.myassistant.util;

import android.content.Context;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.modle.DayItem;
import com.zucc.zwy1317.myassistant.modle.ScheduleBean;
import com.zucc.zwy1317.myassistant.modle.WeekItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 用于一些日历信息的管理
 */
public class CalendarManager {


    private static CalendarManager mInstance;//单例模型

    private Context mContext;
    private Locale mLocale;
    private Calendar mToday = Calendar.getInstance();

    //SimpleDateFormat 是一个以国别敏感的方式格式化和分析数据的具体类。 它允许格式化 (date -> text)、语法分析 (text -> date)和标准化。
    private SimpleDateFormat mWeekdayFormatter;
    private SimpleDateFormat mMonthHalfNameFormat;
    private SimpleDateFormat mMonthDateFormat;
    private SimpleDateFormat mGetDateAndWeekFormat;
    private SimpleDateFormat mGetTimeFormat;

    private List<DayItem> mDays = new ArrayList<>();
    private List<WeekItem> mWeeks = new ArrayList<>();
    private List<ScheduleBean> mSchedules = new ArrayList<>();

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
        mMonthDateFormat = new SimpleDateFormat(mContext.getString(R.string.month_name_format), mLocale);
        mGetDateAndWeekFormat = new SimpleDateFormat(mContext.getString(R.string.date_and_week_format),mLocale);
        mGetTimeFormat = new SimpleDateFormat(mContext.getString(R.string.time_format),mLocale);
    }

    public Locale getLocale() {
        return mLocale;
    }

    public Calendar getToday() {
        return mToday;
    }

    public void setToday(Calendar mToday) {
        this.mToday = mToday;
    }

    public SimpleDateFormat getWeekdayFormatter() {
        return mWeekdayFormatter;
    }

    public SimpleDateFormat getGetDateAndWeekFormat(){
        return mGetDateAndWeekFormat;
    }

    public SimpleDateFormat getGetTimeFormat(){
        return mGetTimeFormat;
    }

    public List<DayItem> getDays() {
        return mDays;
    }

    public List<WeekItem> getWeeks() {
        return mWeeks;
    }

    public List<ScheduleBean> getSchedules() {
        return mSchedules;
    }

    public void setSchedules(List<ScheduleBean> mSchedules) {
        this.mSchedules = mSchedules;
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
        mSchedules.clear();

        Calendar mMaxCal = Calendar.getInstance(mLocale);
        mWeekCounter = Calendar.getInstance(mLocale);

        mMaxCal.setTime(maxDate.getTime());

        // -1,不包括最大的那天 比如我是1990 01 01到2000 01 01，我不要2000 01 01
        mMaxCal.add(Calendar.MINUTE, -1);

        mWeekCounter.setTime(minDate.getTime());

        int maxMonth = mMaxCal.get(Calendar.MONTH);//日历所存在的最大月
        int maxYear = mMaxCal.get(Calendar.YEAR);//日历所存在的最大年

        while ((mWeekCounter.get(Calendar.YEAR) < maxYear
                || mWeekCounter.get(Calendar.MONTH) <= maxMonth)
                && mWeekCounter.get(Calendar.YEAR) < maxYear + 1) {
            WeekItem weekItem = getWeekItem(mWeekCounter);
            mWeeks.add(weekItem);

            //加1周，循环
            mWeekCounter.add(Calendar.WEEK_OF_YEAR, 1);
        }
    }

    private WeekItem getWeekItem(Calendar startCal) {

        Calendar cal = Calendar.getInstance(mLocale);
        cal.setTime(startCal.getTime());
        List<DayItem> dayItems = new ArrayList<>();
        String strMouth ;

        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);//传入的这天在这一周中week的位置
        int offset = cal.getFirstDayOfWeek() - firstDayOfWeek;//这一周的第一天-传入的这天在这一周中week的位置


        if (offset > 0) {
            offset -= 7;
        }

        //补全偏移量 获得一周中的第一天 不同地区第一天不同
        cal.add(Calendar.DATE, offset);
        WeekItem weekItem = new WeekItem(cal);

        //向DayBean中添加数据
        for (int c = 0; c < 7; c++) {
            DayItem dayItem = new DayItem(cal);
            strMouth = mMonthHalfNameFormat.format(cal.getTime());
            dayItems.add(dayItem);
            if(dayItem.getValue() == 15){
                weekItem.setMiddleOfMonth(true);
                weekItem.setmLabel(strMouth);
            }
            dayItem.setMonth(strMouth);
            if(DateUtil.sameDay(mToday,cal)){
                dayItem.setToday(true);
            }
            cal.add(Calendar.DATE, 1);
        }
        weekItem.setDayItems(dayItems);
        mDays.addAll(dayItems);
        return weekItem;
    }

    public void loadData(List<ScheduleBean> list) {
        List<DayItem> days = getDays();
        int daysSize = days.size();
        List<ScheduleBean> scheduleList = new ArrayList<>();
        ScheduleBean scheduleBean;
        int i = 0, j = 0;

        for(Date date = days.get(0).getDate().getTime(); j < list.size() && date.after(list.get(j).getDate().getTime()); j++);

        while(i < daysSize && j < list.size()){
            if(DateUtil.sameDay(days.get(i).getDate(),list.get(j).getDate())){
                scheduleBean = list.get(j);
                scheduleBean.setHeadID(i);
                scheduleList.add(scheduleBean);
                j++;
            }else{
                scheduleList.add(new ScheduleBean(i));
                i++;
            }
        }
        while(i < daysSize){
            scheduleList.add(new ScheduleBean(i));
            i++;
        }
        setSchedules(scheduleList);
        //发送消息，EventsFetched 暂时理解为 事件与日期匹配
        BusProvider.getInstance().send(new Events.EventsFetched());
    }

}
