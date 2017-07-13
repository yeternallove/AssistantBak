package com.zucc.zwy1317.myassistant.ui.customview.agenda;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.modle.ScheduleBean;
import com.zucc.zwy1317.myassistant.util.CalendarManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;


public class AgendaAdapter extends BaseAdapter implements StickyListHeadersAdapter {


    /**
     * 当前日期的颜色
     */
    private int mCurrentDayColor = R.color.calendar_agendaHead_current_color;
    private LayoutInflater inflater;
    private Context mContext;
    private List<ScheduleBean> mData;
    private SimpleDateFormat mFormat = CalendarManager.getInstance().getWeekdayFormatter();


    public AgendaAdapter(Context context) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        mData =new ArrayList<>();
        this.mCurrentDayColor = mContext.getResources().getColor(mCurrentDayColor);
    }

    public void updateEvents(List<ScheduleBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public ScheduleBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public long getHeaderId(int position) {
        return mData.get(position).getHeadID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        ScheduleBean scheduleBean = mData.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.agenda_view_sss, parent, false);
            holder.tvEvent = (TextView) convertView.findViewById(R.id.tv_agenda_day_event);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvEvent.setText(scheduleBean.toString());
        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.agenda_view_header,parent,false);
            holder.tvHeadDay = (TextView)convertView.findViewById(R.id.tv_agenda_day_of_month);
            holder.tvHeadWeek = (TextView)convertView.findViewById(R.id.tv_agenda_day_of_week);
            holder.viewCircle = convertView.findViewById(R.id.view_day_circle_selected);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        holder.tvHeadDay.setText(String.valueOf(mData.get(position).getDate().get(Calendar.DAY_OF_MONTH)));
        holder.tvHeadWeek.setText(mFormat.format(mData.get(position).getDate().getTime()));
        return convertView;
    }


    private class HeaderViewHolder
    {
        TextView tvHeadDay;
        TextView tvHeadWeek;
        View viewCircle;
    }
    private class ViewHolder{
        TextView tvEvent;
    }
}
