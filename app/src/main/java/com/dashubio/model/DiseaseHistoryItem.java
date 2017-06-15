package com.dashubio.model;

import java.io.Serializable;

/**
 * 疾病历史（手术、外伤、输血）Item
 */
public class DiseaseHistoryItem implements Serializable{

    private String name = "";//名称

    private String  dateTime = "";//时间


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
