package com.dashubio.model;


import java.io.Serializable;

/**
 * 预警标题接口
 */
public class HealthWarningCategory implements Serializable{

    private String id;
    private String name;

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
}
