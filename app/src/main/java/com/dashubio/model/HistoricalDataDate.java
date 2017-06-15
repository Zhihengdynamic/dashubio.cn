package com.dashubio.model;


import java.io.Serializable;
import java.util.List;

/**
 * 历史数据日期
 */
public class HistoricalDataDate implements Serializable{

    private String year;
    private List<String> month;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<String> getMonth() {
        return month;
    }

    public void setMonth(List<String> month) {
        this.month = month;
    }
}
