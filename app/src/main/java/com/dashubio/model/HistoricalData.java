package com.dashubio.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 历史数据
 */
public class HistoricalData implements Serializable{

    private String id;

    @SerializedName("m_id")
    private String userId;

    private String addtime;
    private String project;

    @SerializedName("val")
    private String value;
    private String unit;
    private String name;
    private String age;
    private String warning;

    private boolean showHealthInstruction = false;//是否展示健康指导----健康指导界面需要用


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public boolean isShowHealthInstruction() {
        return showHealthInstruction;
    }

    public void setShowHealthInstruction(boolean showHealthInstruction) {
        this.showHealthInstruction = showHealthInstruction;
    }
}
