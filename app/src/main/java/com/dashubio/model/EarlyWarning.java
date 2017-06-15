package com.dashubio.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 预警设置
 */
public class EarlyWarning implements Serializable{

    private String id;

    @SerializedName("p_name")
    private String name;
    private String max_v;
    private String min_v;
    private String unit;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMax_v() {
        return max_v;
    }

    public void setMax_v(String max_v) {
        this.max_v = max_v;
    }

    public String getMin_v() {
        return min_v;
    }

    public void setMin_v(String min_v) {
        this.min_v = min_v;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
