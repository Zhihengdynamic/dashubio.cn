package com.dashubio.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 健康报告
 */
public class HealthReportItem implements Serializable {

    private String year;//

    @SerializedName("det")
    private List<HealthReportMonthItem> reportMonthList;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<HealthReportMonthItem> getReportMonthList() {
        return reportMonthList;
    }

    public void setReportMonthList(List<HealthReportMonthItem> reportMonthList) {
        this.reportMonthList = reportMonthList;
    }
}
