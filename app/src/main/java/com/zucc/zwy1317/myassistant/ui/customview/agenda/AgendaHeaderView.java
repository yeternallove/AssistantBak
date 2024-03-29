package com.zucc.zwy1317.myassistant.ui.customview.agenda;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zucc.zwy1317.myassistant.R;

/**
 * Header view for the StickyHeaderListView of the agenda view
 * AgendaListView 中用于停靠的顶部 view AgendaHeaderView
 */
public class AgendaHeaderView extends LinearLayout {
    private Context context;

    private int[] imagId = new int[]{R.drawable.month_one, R.drawable.month_two, R.drawable.month_three, R.drawable.month_four
            , R.drawable.month_five, R.drawable.month_six, R.drawable.month_seven, R.drawable.month_eight, R.drawable.month_nine, R.drawable.month_ten,
            R.drawable.month_eleven, R.drawable.month_twelve};

    /**
     * 引入布局文件
     *
     * @param parent AgendaHeaderView 的父布局 ，应该就是AgendaListView
     * @return
     */
    public static AgendaHeaderView inflate(ViewGroup parent) {
        return (AgendaHeaderView) LayoutInflater.from(parent.getContext()).inflate(R.layout.agenda_view_header, parent, false);
    }

    public AgendaHeaderView(Context context) {
        super(context);
        this.context = context;
    }

    public AgendaHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public AgendaHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    /**
     * 设置 headerView 上文字的样式,内容
     *
     * @param day                 需要设置的日期
     * @param currentDayTextColor 当前设置日期的颜色
     */
//    public void setDay(Calendar day, int currentDayTextColor) {
//
//        //初始化控件
//        TextView txtDayOfMonth = (TextView) findViewById(R.id.tv_agenda_day_of_month);
//        TextView txtDayOfWeek = (TextView) findViewById(R.id.tv_agenda_day_of_week);
//        View circleView = findViewById(R.id.view_day_circle_selected);
//
//        //普通日期为灰色
//        //list 左边日期的颜色
//        txtDayOfMonth.setTextColor(getResources().getColor(R.color.calendar_text_default));
//        //list 左边星期的颜色
//        txtDayOfWeek.setTextColor(getResources().getColor(R.color.calendar_text_default));
//
//        //得到每个月的第一天
//        List<Calendar> list = CalendarManager.getInstance().getFirstDayOfMonth();
//        if ((int)day.get(Calendar.DAY_OF_MONTH) == 1) {
//            for (int i = 0; i < list.size(); i++) {
//                if ((int)day.get(Calendar.MONTH) == i) {
//                    setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(120)));
//
//                    setBackground(getResources().getDrawable(imagId[i]));
//                    //每月第一天为黑色
//                    //list 左边日期的颜色
//                    txtDayOfMonth.setTextColor(getResources().getColor(R.color.calendar_text_day));
//                    //list 左边星期的颜色
//                    txtDayOfWeek.setTextColor(getResources().getColor(R.color.calendar_text_day));
//                }
//            }
//        } else {
//            setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            setBackgroundColor(Color.TRANSPARENT);
//        }
//
//        //得到今天
//        Calendar today = CalendarManager.getInstance().getToday();
//
//        //设置星期格式
//        SimpleDateFormat dayWeekFormatter = new SimpleDateFormat("E");
//
//
//        //如果当前日期为today,则显示为蓝色,同时设置circleView的颜色粗细,否则就隐藏circleView
//        if (Da.sameDate(day, today)) {
//            txtDayOfMonth.setTextColor(currentDayTextColor);
//            txtDayOfWeek.setTextColor(currentDayTextColor);
////            circleView.setVisibility(VISIBLE);
//        } else {
//            circleView.setVisibility(INVISIBLE);
//        }
//
//        //添加文字
//        txtDayOfMonth.setText(String.valueOf(day.get(Calendar.DAY_OF_MONTH)));
//        txtDayOfWeek.setText(dayWeekFormatter.format(day.getTime()));
//    }


    // dp->px
    private int dip2px(float dipValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }
}