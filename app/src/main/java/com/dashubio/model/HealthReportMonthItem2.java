package com.dashubio.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 健康报告月
 */
public class HealthReportMonthItem2 implements Serializable {

    private String month;//

    @SerializedName("pro")
    private List<HealthReportProjectItem> projectList;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

   
}
