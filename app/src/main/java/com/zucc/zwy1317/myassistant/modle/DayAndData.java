package com.zucc.zwy1317.myassistant.modle;

import com.zucc.zwy1317.myassistant.modle.interfaces.DayOfData;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/9 20:24
 */

public class DayAndData {
    private Date date;
    private List<DayOfData> dayOfData;

    public DayAndData(Date date,List<DayOfData> dayOfData){
        this.date = date;
        this.dayOfData = dayOfData;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<DayOfData> getDayOfData() {
        return dayOfData;
    }

    public void setDayOfData(List<DayOfData> dayOfData) {
        this.dayOfData = dayOfData;
    }
}
