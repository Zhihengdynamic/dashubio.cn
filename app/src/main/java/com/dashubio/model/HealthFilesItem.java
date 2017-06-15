package com.dashubio.model;

import java.io.Serializable;

/**
 * 健康档案Item（疾病等）
 */
public class HealthFilesItem implements Serializable {

    private int id;//
    private String name;//


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else {
            if (obj instanceof HealthFilesItem) {
                HealthFilesItem c = (HealthFilesItem) obj;
                return this.getId() == c.getId();
            }
        }
        return false;
    }


}
