package com.dashubio.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 留言
 */
public class LeavingMessage implements Serializable{

    private String id;

    private String name;//姓名

    private String title;

    private String content;

    @SerializedName("addtime")
    private String time;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
